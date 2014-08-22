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
  
  $("#fileBrowserLauncher").click(function(event){
    event.preventDefault();
    $("#excelTemplate").trigger("click");
  });
  
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
    
    var locationTypeID = $(location).find("[name$='type.id']").val();
    $(location).find(".locationIndex strong").text((index + 1) + ".");

    if( locationTypeID == 1){
      // If Location is of type region
      
      elementName = "regionsSaved[" + index + "].";
      $(location).find("[name$='].id']").attr("name", elementName + "id");
      $(location).find("[name$='name']").attr("name", "name");
      $(location).find("[name$='type.id']").attr("name", "type.id");
      $(location).find("[name$='geoPosition.id']").attr("name", "geoPosition.id");
      $(location).find("[name$='geoPosition.latitude']").attr("name", "geoPosition.latitude").attr("placeholder", "Latitude");
      $(location).find("[name$='geoPosition.longitude']").attr("name", "geoPosition.longitude").attr("placeholder", "Longitude");
      
    }else if(locationTypeID == 2){
      // If location is of type country

      elementName = "countriesSaved[" + index + "].";
      $(location).find("[name$='].id']").attr("name", elementName + "id");
      $(location).find("[name$='name']").attr("name", "name");
      $(location).find("[name$='type.id']").attr("name", "type.id");
      $(location).find("[name$='geoPosition.id']").attr("name", "geoPosition.id");
      $(location).find("[name$='geoPosition.latitude']").attr("name", "geoPosition.latitude").attr("placeholder", "Latitude");
      $(location).find("[name$='geoPosition.longitude']").attr("name", "geoPosition.longitude").attr("placeholder", "Longitude");
    }else{
      // If location is of other type

      elementName = "otherLocationsSaved[" + index + "].";
      $(location).find("[name$='].id']").attr("name", elementName + "id");
      $(location).find("[name$='name']").attr("name", elementName + "name");
      $(location).find("[name$='type.id']").attr("name", elementName + "type.id");
      $(location).find("[name$='geoPosition.id']").attr("name", elementName + "geoPosition.id");
      $(location).find("[name$='geoPosition.latitude']").attr("name", elementName + "geoPosition.latitude").attr("placeholder", "Latitude");
      $(location).find("[name$='geoPosition.longitude']").attr("name", elementName + "geoPosition.longitude").attr("placeholder", "Longitude");
    }
    
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

function reverseGeoCoding($location){
  var latitude = $location.find("input[name$='latitude']").val();
  var longitude = $location.find("input[name$='longitude']").val();
  
  console.log(latitude + " - " + longitude);
  
}
