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
 * Get the geo-coordinates if it had been defined
 * calling the function getOtherSitesCoordinates(otherSiteID)
 * in the parent window.
 * 
 * @returns an array with the coordinates or null if it wasn't defined
 */

function getGeoCoordinates(){
  var otherSiteID = getOtherSiteID();
  var geoCoordinates = window.opener.getOtherSitesCoordinates(otherSiteID);
  console.log(otherSiteID  + " - " + geoCoordinates);
  return geoCoordinates;
}

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
  // Get the otherSite id from the URL
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

  
  // Check if the coordinates are specified,
  var coordinates = getGeoCoordinates(); 
  console.log(coordinates);
  if(coordinates != null){
    var location = new google.maps.LatLng(coordinates.lat, coordinates.lng);
    var bounds = new google.maps.LatLngBounds(location, location);
    
    map.setCenter(location);
    map.fitBounds(bounds);
    
    // Put the marker
    marker = new google.maps.Marker({
      position : location,
      map : map
    });

    // Set the values of longitude and latitude
    latitude = location.lat();
    longitude = location.lng();

    // Add the event to the marker for delete it on click
    google.maps.event.addListener(marker, "click", function(e) {
      marker.setMap(null);

      latitude = -1;
      longitude = -1;
    });
    markerExists = true;
  }else{
  
    // Center the map in the country selected
    var geocoder = new google.maps.Geocoder();
    var location = getCountryLocation();
  
    geocoder.geocode({
      'address' : location
    }, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location, 13);
        map.fitBounds(results[0].geometry.bounds);
        //map.setZoom(4);
      } else {
        alert("Could not find country selected");
      }
    });

  }
  
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