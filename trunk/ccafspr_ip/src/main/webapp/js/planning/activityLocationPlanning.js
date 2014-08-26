// Global vars
var map;
var markers = [];

$(document).ready(init);

function init(){
  attachEvents();
  loadMap();
  setLocationsMarkers();
}

function attachEvents(){
  // Locations add/remove Events
  $("#addLocationLink").on("click", addLocationEvent);
  $("#locationsBlock .removeButton").on("click", removeLocationEvent);
  // Change Location type
  $("[name$='type.id']").on("change", changeTypeEvent);
  
  // isGlobale Change
  $("#isGlobal").on("change", changeGlobalState);
  
  $("[name$='geoPosition.latitude'], [name$='geoPosition.longitude']").on("keyup", reloadMarkers);
  
  $("#fileBrowserLauncher").click(function(event){
    event.preventDefault();
    $("#excelTemplate").trigger("click");
  });
  
}

function changeGlobalState(e){
  if ($(e.target).is(':checked')) {
    $("#locationsBlock").fadeOut("slow");
    disableLocations(true);
  } else {
    $("#locationsBlock").fadeIn("slow");
    disableLocations(false);
  }
  
}

function disableLocations(state){
  $("#locationsBlock .location").each(function(index,location){
    $(location).find("input,select").attr("disabled", state);
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
  $(e.target).parent().hide("slow", function(){
    $(this).remove();
    setLocationIndex();
  });
}

function setLocationIndex(){
  reloadMarkers();
  var elementName;
  $("#locationsBlock .location").each(function(index,location){
    
    var locationTypeID = $(location).find("[name$='type.id']").val();
    $(location).find(".locationIndex strong").text((index + 1) + ".");
    
    if (locationTypeID == 1) {
      // If Location is of type region
      
      elementName = "regionsSaved[" + index + "].";
      $(location).find("[name$='].id']").attr("name", elementName + "id");
      $(location).find("[name$='name']").attr("name", "name");
      $(location).find("[name$='type.id']").attr("name", "type.id");
      $(location).find("[name$='geoPosition.id']").attr("name", "geoPosition.id");
      $(location).find("[name$='geoPosition.latitude']").attr("name", "geoPosition.latitude").attr("placeholder", "Latitude");
      $(location).find("[name$='geoPosition.longitude']").attr("name", "geoPosition.longitude").attr("placeholder", "Longitude");
      
    } else if (locationTypeID == 2) {
      // If location is of type country
      
      elementName = "countriesSaved[" + index + "].";
      $(location).find("[name$='].id']").attr("name", elementName + "id");
      $(location).find("[name$='name']").attr("name", "name");
      $(location).find("[name$='type.id']").attr("name", "type.id");
      $(location).find("[name$='geoPosition.id']").attr("name", "geoPosition.id");
      $(location).find("[name$='geoPosition.latitude']").attr("name", "geoPosition.latitude").attr("placeholder", "Latitude");
      $(location).find("[name$='geoPosition.longitude']").attr("name", "geoPosition.longitude").attr("placeholder", "Longitude");
    } else {
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

function loadMap(){
  geocoder = new google.maps.Geocoder();
  var style = [
      {
        "featureType" : "water",
        "stylers" : [
            {
              "visibility" : "on"
            }, {
              "color" : "#b5cbe4"
            }
        ]
      }, {
        "featureType" : "landscape",
        "stylers" : [
          {
            "color" : "#efefef"
          }
        ]
      }, {
        "featureType" : "road.highway",
        "elementType" : "geometry",
        "stylers" : [
          {
            "color" : "#83a5b0"
          }
        ]
      }, {
        "featureType" : "road.arterial",
        "elementType" : "geometry",
        "stylers" : [
          {
            "color" : "#bdcdd3"
          }
        ]
      }, {
        "featureType" : "road.local",
        "elementType" : "geometry",
        "stylers" : [
          {
            "color" : "#ffffff"
          }
        ]
      }, {
        "featureType" : "poi.park",
        "elementType" : "geometry",
        "stylers" : [
          {
            "color" : "#e3eed3"
          }
        ]
      }, {
        "featureType" : "administrative",
        "stylers" : [
            {
              "visibility" : "on"
            }, {
              "lightness" : 33
            }
        ]
      }, {
        "featureType" : "road"
      }, {
        "featureType" : "poi.park",
        "elementType" : "labels",
        "stylers" : [
            {
              "visibility" : "on"
            }, {
              "lightness" : 20
            }
        ]
      }, {}, {
        "featureType" : "road",
        "stylers" : [
          {
            "lightness" : 20
          }
        ]
      }
  ];
  map = new google.maps.Map(document.getElementById("activityLocations-map"), {
    center : new google.maps.LatLng(14.41, -12.52),
    zoom : 2,
    mapTypeId : 'roadmap',
    styles : style
  });
}

function setLocationsMarkers(){
  $("#locationsBlock .location").each(function(index,location){
    var latitude = $(location).find("[name$='geoPosition.latitude']").val();
    var longitude = $(location).find("[name$='geoPosition.longitude']").val();
    if (isCoordinateValid(latitude, longitude)) {
      makeMarker({
        latitude : latitude,
        longitude : longitude,
        name : $(location).find("[name$='name']").val()
      });
    }
  });
}

function isCoordinateValid(latitude,longitude){
  if (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) {
    return true;
  } else {
    return false;
  }
}

function makeMarker(data){
  var point = new google.maps.LatLng(data.latitude, data.longitude);
  var marker = new google.maps.Marker({
    map : map,
    position : point,
    icon : '../../../images/global/otherSite-marker.png',
    id : 8
  });
  var html = "<strong>" + data.name + "</strong>";
  var infoWindow = new google.maps.InfoWindow;
  google.maps.event.addListener(marker, 'click', function(){
    infoWindow.setContent(html);
    infoWindow.open(map, marker);
    console.log(marker);
  });
  markers.push(marker);
}

function reloadMarkers(){
  deleteMarkers();
  setLocationsMarkers();
}

function deleteMarkers(){
  setAllMap(null);
  markers = [];
}

// Sets the map on all markers in the array.
function setAllMap(map){
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}
