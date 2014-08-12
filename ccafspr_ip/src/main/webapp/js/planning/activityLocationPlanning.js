$(document).ready(init);

function init(){
  attachEvents();
}

function attachEvents(){
  // Locations add/remove Events
  $("#addLocationLink").on("click", addLocationEvent);
  $("#locationsBlock .removeButton").on("click", removeLocationEvent);
  
  // Change Location type
  $("[name$='type.id']").on("change", changeTypeEvent);
  
  //
  $("[name$='geoPosition.latitude'], [name$='geoPosition.longitude']").on("keydown", isNumber);
  
}

function addLocationEvent(e){
  e.preventDefault();
  $newElement = $("#locationTemplateExisting").clone(true).addClass("location").removeAttr("id");
  $newElement.find(".removeButton").click(removeLocationEvent);
  $(e.target).parent().before($newElement);
  $newElement.fadeIn("slow");
  setLocationIndex();
}
function removeLocationEvent(e){
  e.preventDefault();
  console.log($(".location img").index(e.target));
  $(e.target).parent().hide("slow", function(){
    $(this).remove();
    setLocationIndex();
  });
}

function setLocationIndex(){
  var elementName;
  $("#locationsBlock .location").each(function(index,location){
    $(location).find(".locationIndex strong").text((index + 1) + ".");
    // Set inputs names
    elementName = "activity.locations[" + index + "].";
    $(location).find("[name$='].id']").attr("name", elementName + "id");
    $(location).find("[name$='name']").attr("name", elementName + "name");
    $(location).find("[name$='type.id']").attr("name", elementName + "type.id");
    $(location).find("[name$='geoPosition.latitude']").attr("name", elementName + "geoPosition.latitude").attr("placeholder", "Latitude");
    $(location).find("[name$='geoPosition.longitude']").attr("name", elementName + "geoPosition.longitude").attr("placeholder", "Longitude");
  });
}

function changeTypeEvent(e){
  var valId = $(e.target).val();
  var $parent = $(e.target).parent().parent().parent().parent();
  var $selectType = $("#selectTemplate-" + valId);
  if ($selectType.exists()) {
    var $newSelectType = $selectType.clone(true);
    $parent.find(".locationName").empty().html($newSelectType.html());
    $parent.find("[name$='geoPosition.latitude'], [name$='geoPosition.longitude']").attr("disabled", true).val($("#notApplicableText").val());
  } else {
    var $newInputType = $("#inputNameTemplate").clone(true);
    $parent.find(".locationName").empty().html($newInputType.html());
    $parent.find("[name$='geoPosition.latitude'], [name$='geoPosition.longitude']").attr("disabled", false).val("");
    $parent.find("[name$='name']").attr("placeholder", "Name");
  }
  setLocationIndex();
}
