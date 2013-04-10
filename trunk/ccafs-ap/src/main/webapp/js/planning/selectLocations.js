var latitude;
var longitude;

$(document).ready(function() {
  $("#cancel").on("click", function() {
    window.close();
  });

  $("#save").on("click", function() {
    returnValues();
    window.close();
  });

  drawMap();
});

/**
 * Get the country selected calling the function
 * getOtherSiteCountry(otherSiteID) in the parent window
 * 
 * @returns the country identifier
 */

function getCountryLocation() {
  var otherSiteID = getOtherSiteID();
  var countryID = window.opener.getOtherSiteCountry(otherSiteID);
  return countryID;
}

/**
 * Get the other site field identifier from the URL
 * 
 * @returns
 */

function getOtherSiteID() {
  // Get the otherSite id
  var otherSiteID = window.location.search.substring(1).split('=')[1];

  return otherSiteID;
}

/**
 * Set the fields with the corresponding values of longitude and latitude
 * selected calling a function in parent window
 * 
 */
function returnValues() {
  // Call the function in the other window to update the values.
  window.opener.setOtherSiteLocation(getOtherSiteID(), latitude, longitude);
  alert("Values updated successfully");
}

function drawMap() {
  var map;
  var marker;
  var markerExists = false;

  // First load the map
  var mapOptions = {
    zoom : 1,
    center : new google.maps.LatLng(0, 0),
    mapTypeId : google.maps.MapTypeId.TERRAIN
  };
  map = new google.maps.Map(document.getElementById('other_locations'),
      mapOptions);

  // Center the map in the country selected
  var geocoder = new google.maps.Geocoder();
  var location = getCountryLocation();
  geocoder.geocode({
    'address' : location
  }, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      map.setCenter(results[0].geometry.location, 13);
      map.setZoom(4);
    } else {
      alert("Could not find country selected");
    }
  });

  // add onClick event to the map
  google.maps.event.addListener(map, "click", function(e) {

    // If there is a marker delete it
    if (markerExists) {
      marker.setMap(null);
    }

    // Put the marker
    marker = new google.maps.Marker({
      position : e.latLng,
      map : map
    });

    // Set the values of longitude and latitude
    latitude = e.latLng.lat();
    longitude = e.latLng.lng();

    // Add the event to the marker for delete it on click
    google.maps.event.addListener(marker, "click", function(e) {
      marker.setMap(null);

      latitude = -1;
      longitude = -1;
    });
    markerExists = true;
  });
};