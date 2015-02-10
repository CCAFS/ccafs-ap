//Limits for textarea input
var lWordsElemetDesc = 300;

$(document).ready(init);

function init(){
  attachEvents();
  addChosen();
  applyWordCounter($("textarea"), lWordsElemetDesc);
  $("select[id$='mainType']").trigger('change');
}

function attachEvents(){
  // Deliverables Events
  $(".removeDeliverable, .removeNextUser").click(removeElementEvent);
  $("#addDeliverable").on("click", addDeliverableEvent);
  $("select[id$='mainType']").each(function(){
    console.log($(this).attr("id"));
  });
  $("select[id$='mainType']").change(updateDeliverableSubTypeList);
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
  var $newElement = $("#activityDeliverable-999").clone(true).removeAttr("id").addClass("activityDeliverable");
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
    $(element).find("[name$='output']").attr("name", elementName + "output");
    
    $(element).find("[name$='mainType']").attr("id", "activityDeliverables_mainType");
    $(element).find("[name$='type']").attr("id", "activityDeliverables_activity_deliverables_" + index + "__type");
    
    // Update index for nexts users
    $(element).find(".activityNextUser").each(function(i,nextUser){
      var elementName = "activity.deliverables[" + index + "].nextUsers[" + i + "].";
      $(nextUser).attr("id", "activityNextUser-" + index);
      $(nextUser).find("span#index").html(i + 1);
      
      $(nextUser).find("[name$='].id']").attr("name", elementName + "id");
      $(nextUser).find("[name$='user']").attr("name", elementName + "user");
      $(nextUser).find("[name$='expectedChanges']").attr("name", elementName + "expectedChanges");
      $(nextUser).find("[name$='strategies']").attr("name", elementName + "strategies");
      
    });
  });
}

function updateDeliverableSubTypeList(event){
  var $mainTypeSelect = $(event.target);
  var blockIndex = $("select[id$='mainType']").index($mainTypeSelect);
  var $subTypeSelect;
  
  // Check that the select is not inside the template
  if ($mainTypeSelect.attr("id") != "none_mainType") {
    $subTypeSelect = $("#activityDeliverable-" + blockIndex + " select[name$='type'] ");
  } else {
    // The template has index 999
    $subTypeSelect = $("#activityDeliverable-999 select[name$='type'] ");
  }
  
  var source = "../../../json/deliverablesByType.do?deliverableTypeID=" + $mainTypeSelect.val();
  $.getJSON(source).done(function(data){
    // First delete all the options already present in the subtype select
    $subTypeSelect.find("option").remove();
    
    var selectedValue = $("#subTypeSelected_" + blockIndex).val();
    var optionElement;
    $.each(data.subTypes, function(index,subType){
      if (selectedValue == subType.id) {
        optionElement = "<option value='" + subType.id + "' selected >" + subType.name + "</option>";
      } else {
        optionElement = "<option value='" + subType.id + "'>" + subType.name + "</option>";
      }
      
      $subTypeSelect.append(optionElement);
    });
    // Refresh the plugin in order to show the changes
    $subTypeSelect.trigger("liszt:updated");
  }).fail(function(){
    console.log("error");
  });
  
}