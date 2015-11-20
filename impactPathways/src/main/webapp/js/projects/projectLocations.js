// Global vars
var map;
var markers = [];
var countID;
var excelTemplate;
$(document).ready(init);
var lWordsLessons = 100;

function init() {
  countID = $("#locationsBlock .location").length;
  attachEvents();
  $('.loadingBlock').hide().next().fadeIn(500);
  if($("#isGlobal").exists()) {
    $("input.globalCheck").trigger("change");
    $("#locationsBlock").fadeOut("slow");
  } else {
    loadMap();
    setLocationsMarkers();
  }
  applyWordCounter($("#lessons textarea"), lWordsLessons);
}

function attachEvents() {
  // Locations add/remove Events
  $("#addLocationLink").on("click", addLocationEvent);
  $("#locationsBlock .removeButton").on("click", removeLocationEvent);

  // Change Location type
  $("select.locationType").on("change", changeTypeEvent);

  // isGlobal Change
  $("#project\\.global").on("change", changeGlobalState);

  // Validate if coordinate is valid then take actions
  $("input.latitude, input.longitude").on("keyup", markerAction);
  $("input.latitude, input.longitude").on("focus", markerAction);

  // Updating project.locations input when his fields changes
  $(".locationType, .latitude, .longitude, .locationName").on("change", updateLocationInput);

  // Validate changes in form information
  validateEvent([
    "#justification"
  ]);
}

function updateLocationInput(e) {
  var $location = $(e.target).parents('.location');
  var loc =
      [
          $location.find('.locationType').val(), $location.find('input.latitude').val(),
          $location.find('input.longitude').val(), $location.find('input.locationName').val(),
          $location.find('input.locationId').val()
      ];
  if($location.find('select.locationName').val()) {
    $location.find('select[name$=locations]').val($location.find('select.locationName').val());
  } else {
    $location.find('input[name$=locations]').val(loc.join('|s|'));
  }
}

function changeGlobalState(e) {
  if($(e.target).is(':checked')) {
    $("#locationsBlock").fadeOut("slow");
    disableLocations(true);
    clearMarkers();
    $("#projectLocations-map").html(
        "<img id='global' src='" + baseURL + "/images/global/global-map.png'/>" + "<p class='global'>"
            + $("#isGlobalText").val() + "</p>");
  } else {
    $("#locationsBlock").fadeIn("slow");
    disableLocations(false);
    loadMap();
    showMarkers();
  }
}

function disableLocations(state) {
  $("#locationsBlock .location").each(function(index,location) {
    $(location).find("input,select").each(function(index,input) {
      if(!$(this).hasClass("notApplicable")) {
        $(this).attr("disabled", state);
      }
    });
  });
}

function addLocationEvent(e) {
  e.preventDefault();
  $newElement =
      $("#locationTemplateExisting").clone(true).removeAttr("id").attr("id", "location-" + (countID++)).addClass(
          "location");
  $newElement.find(".removeButton").click(removeLocationEvent);
  $(e.target).parent().parent().find("#fields").append($newElement);
  $newElement.fadeIn("slow");
  setLocationIndex();
}

function removeLocationEvent(e) {
  e.preventDefault();
  var locationId = $(e.target).parent().attr("id").split("-")[1];
  $(e.target).parent().hide("slow", function() {
    $(this).remove();
    removeMarker(locationId);
    setLocationIndex();
  });
}

function setLocationIndex() {
  // var elementName;
  $("#locationsBlock .location").each(function(index,location) {
    $(location).find(".locationIndex strong").text((index + 1) + ".");
/*
 * var locationTypeID = $(location).find("[name$='type.id']").val(); $(location).attr("id", "location-" + index);
 * if(locationTypeID == 1) { // If Location is of type region } else if(locationTypeID == 2) { // If location is of type
 * country } else if((locationTypeID == 10) || (locationTypeID == 11)) { // If location is of type CCAFS Site } else { //
 * If location is of other type }
 */
  });
}

function changeTypeEvent(e) {
  var valId = $(e.target).val();
  var $parent = $(e.target).parent().parent().parent().parent();
  var locationId = $parent.attr("id").split("-")[1];
  var $selectType = $("#selectTemplate-" + valId);
  var $latitudeField = $parent.find("input.latitude");
  var $longitudeField = $parent.find("input.longitude");

  if($selectType.exists() || (valId == "-1")) {
    var $newSelectType = $selectType.clone(true);
    if(valId == "-1") {
      $newSelectType = $("#inputNameTemplate").clone(true);
    }
    $parent.find(".locationName").empty().html($newSelectType.html());
    $parent.find("input.latitude, input.longitude").addClass("notApplicable").attr("disabled", true).val(
        $("#notApplicableText").val());
    // If marker exist, remove the marker
    if(typeof markers[locationId] !== 'undefined') {
      removeMarker(locationId);
    }
  } else {
    var $newInputType = $("#inputNameTemplate").clone(true);
    $parent.find(".locationName").empty().html($newInputType.html());
    $parent.find("input.latitude, input.longitude").removeClass("notApplicable").attr("disabled", false);
    $parent.find("input.locationName").attr("placeholder", "Name");
    var lat = map.getCenter().lat();
    var lng = map.getCenter().lng();
    $latitudeField.val(lat);
    $longitudeField.val(lng);

    var latitude = $latitudeField.val();
    var longitude = $longitudeField.val();

    // If marker doesn't exist create the marker
    if(typeof markers[locationId] === 'undefined') {
      // checks whether a coordinate is valid
      if(isCoordinateValid(latitude, longitude)) {
        makeMarker({
            latitude: latitude,
            longitude: longitude,
            name: $parent.find("input.locationName").val(),
            id: locationId
        });
      }
    }
  }
  setLocationIndex();
}

function loadMap() {
  var style = [
      {
          "featureType": "water",
          "stylers": [
              {
                "visibility": "on"
              }, {
                "color": "#b5cbe4"
              }
          ]
      }, {
          "featureType": "landscape",
          "stylers": [
            {
              "color": "#efefef"
            }
          ]
      }, {
          "featureType": "road.highway",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#83a5b0"
            }
          ]
      }, {
          "featureType": "road.arterial",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#bdcdd3"
            }
          ]
      }, {
          "featureType": "road.local",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#ffffff"
            }
          ]
      }, {
          "featureType": "poi.park",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#e3eed3"
            }
          ]
      }, {
          "featureType": "administrative",
          "stylers": [
              {
                "visibility": "on"
              }, {
                "lightness": 33
              }
          ]
      }, {
        "featureType": "road"
      }, {
          "featureType": "poi.park",
          "elementType": "labels",
          "stylers": [
              {
                "visibility": "on"
              }, {
                "lightness": 20
              }
          ]
      }, {}, {
          "featureType": "road",
          "stylers": [
            {
              "lightness": 20
            }
          ]
      }
  ];
  map = new google.maps.Map(document.getElementById("projectLocations-map"), {
      center: new google.maps.LatLng(14.41, -12.52),
      zoom: 2,
      mapTypeId: 'roadmap',
      styles: style
  });
}

function setLocationsMarkers() {
  $("#locationsBlock .location").each(function(index,location) {
    var latitude = $(location).find("input.latitude").val();
    var longitude = $(location).find("input.longitude").val();
    // checks whether a coordinate is valid
    if(isCoordinateValid(latitude, longitude)) {
      makeMarker({
          latitude: latitude,
          longitude: longitude,
          name: $(location).find("input.locationName").val(),
          id: $(location).attr("id").split("-")[1]
      });
    }
  });
}

function markerAction(e) {
  var $parent = $(e.target).parent().parent().parent();
  var locationId = $parent.attr("id").split("-")[1];
  var $coordinatesFields = $parent.find("input.longitude, input.latitude");
  var latitude = $parent.find("input.latitude").val();
  var longitude = $parent.find("input.longitude").val();
  var marker = markers[locationId];
  if(isCoordinateValid(latitude, longitude)) {
    if(e.type == "keyup") {
      marker.setPosition(new google.maps.LatLng(latitude, longitude));
    } else if(e.type == "focus") {
      marker.setAnimation(google.maps.Animation.BOUNCE);
      setTimeout(function() {
        marker.setAnimation(null);
      }, 1000);
    }
    $coordinatesFields.removeClass("fieldError");
  } else {
    $coordinatesFields.addClass("fieldError");
  }
}

function makeMarker(data) {
  var point = new google.maps.LatLng(data.latitude, data.longitude);
  var marker = new google.maps.Marker({
      id: data.id,
      map: map,
      position: point,
      icon: baseURL + '/images/global/otherSite-marker.png',
      draggable: ($('#isEditable').val() == 1),
      animation: google.maps.Animation.DROP
  });
  var html = "<div class='infoWindow'>" + data.name + "</div>";
  var infoWindow = new google.maps.InfoWindow;
  // Event when marker is clicked
  google.maps.event.addListener(marker, 'click', function(e) {
    infoWindow.setContent(html);
    infoWindow.open(map, marker);
  });
  // Event when marker is mouseover
  google.maps.event.addListener(marker, 'mouseover', function(e) {
    $("#location-" + marker.id).addClass("selected");
  });
  // Event when marker is mouseout
  google.maps.event.addListener(marker, 'mouseout', function(e) {
    $("#location-" + marker.id).removeClass("selected");
  });
  // Event when marker is dragged
  google.maps.event.addListener(marker, 'dragend', function(e) {
    var longitude = e.latLng.lng();
    var latitude = e.latLng.lat();
    $("#location-" + marker.id).find("input.longitude").val(longitude).trigger("change");
    $("#location-" + marker.id).find("input.latitude").val(latitude).trigger("change");
  });

  markers[data.id] = marker;
}

// Delete all markers in the map
function deleteMarkers() {
  setAllMap(null);
  markers = [];
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setAllMap(null);
}

// Shows any markers currently in the array.
function showMarkers() {
  setAllMap(map);
}

// Remove individual marker by id
function removeMarker(id) {
  marker = markers[id];
  marker.setMap(null);
  delete markers[id];
}

// Sets the map on all markers in the array.
function setAllMap(map) {
  $.each(markers, function(index,marker) {
    if(marker) {
      marker.setMap(map);
    }
  });
}

// checks whether the coordinate is valid
function isCoordinateValid(latitude,longitude) {
  if(latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) {
    return true;
  } else {
    return false;
  }
}
