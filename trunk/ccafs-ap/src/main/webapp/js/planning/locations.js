$(document).ready(function() {

  $(".removeOtherSite").on("click", function(event) {
    event.preventDefault();
    $(event.target).parent().parent().hide("slow", function() {
      $(event.target).parent().parent().remove();
      renameOtherSites();
    });
  });

  $(".addOtherSite").on("click", function(event) {
    event.preventDefault();
    var newOtherSite = $("#otherSiteTemplate .otherSite").clone(true);
    $("#addOtherSitesBlock").before(newOtherSite);
    renameOtherSites();
    newOtherSite.show("slow");
  });

  $("#activity\\.global").on("change", function() {
    if ($(this).is(':checked')) {
      // If the activity is global hide the other options
      $("#regionsLocations").hide("slow");
      $("#countryLocations").hide("slow");
      $("#bsLocations").hide("slow");
      $("#otherSites").hide("slow");

      // Then reset the values

      // Regions
      $("#regionsLocations").find(":checked").each(function() {
        $(this).attr('checked', false);
      });

      // Countries locations
      $("#locations_activity_countries").val('').trigger('liszt:updated');

      // BS locations
      $("#bsLocations").find(":checked").each(function() {
        $(this).attr('checked', false);
      });

      // Other sites
      $("#otherSites").find(".otherSite").each(function(index) {
        if (index == 0) {
          $(this).find(".countries").val('').trigger('liszt:updated');
          $(this).find("[id$=latitude]").val("");
          $(this).find("[id$=longitude]").val("");
          $(this).find("[id$=details]").val("");
        } else {
          $(this).remove();
        }
      });

    } else {
      $("#regionsLocations").show("slow");
      $("#countryLocations").show("slow");
      $("#bsLocations").show("slow");
      $("#otherSites").show("slow");
    }
  });

  popups();
  addChosen();
});

function renameOtherSites() {
  $("fieldset .otherSite").each(
      function(index, contactPerson) {
        // Block id
        $(this).attr("id", "otherSites-" + index);
        // Country
        $(this).find("[name$='country']").attr("id",
            "locations_activity_otherLocations_" + index + "__country");
        $(this).find("[name$='country']").attr("name",
            "activity.otherLocations[" + index + "].country");
        // Activate the chosen plugin
        var selectList = $(this).find(".countries");
        $(selectList).chosen();
        // Other site id
        $(this).find("[name$='id']").attr("name",
            "activity.otherLocations[" + index + "].id");
        // Latitude
        $(this).find("[name$='latitude']").attr("name",
            "activity.otherLocations[" + index + "].latitude");
        $(this).find("[name$='latitude']").attr("id",
            "activity.otherLocations[" + index + "].latitude");
        // Longitude
        $(this).find("[name$='longitude']").attr("name",
            "activity.otherLocations[" + index + "].longitude");
        $(this).find("[name$='longitude']").attr("id",
            "activity.otherLocations[" + index + "].longitude");
        // Details
        $(this).find("[name$='details']").attr("name",
            "activity.otherLocations[" + index + "].details");
        // Geo location link
        console.log($("#geoLocationLink").attr('href'));
        $(this).find(".popup").attr("id", '');
        $(this).find(".popup").attr("href",
            $("#geoLocationLink").attr('href') + index);
      });
}

//Activate the chosen plugin to the countries inputs
function addChosen() {
  $(".countries").each(function() {
    // Check if its not the template countries field
    if ($(this).attr("name") != 'country') {
      $(this).chosen();
    }
  });
}

function drawRegionsMap() {
  var map;
  var activityID = $("#activityID").val();

  // ---------- Load other locations ------------------

  // First load the map
  var mapOptions = {
    zoom : 8,
    center : new google.maps.LatLng(-34.397, 150.644),
    mapTypeId : google.maps.MapTypeId.TERRAIN
  };
  map = new google.maps.Map(document.getElementById('other_locations'),
      mapOptions);

  // --------------------------- BORRAR -----------------------------
  google.maps.event.addListener(map, "click", function(e) {
    console.log("lat: " + e.latLng.lat());
    console.log("long: " + e.latLng.lng());
  });
};

/**
 * This function is called from popup window, set the values of latitude and
 * longitude specified for the given otherSite
 * 
 * @param otherSiteId
 * @param latitude
 * @param longitude
 */
function setOtherSiteLocation(otherSiteId, latitude, longitude) {
  var latitudeField = $("#activity\\.otherLocations\\[" + otherSiteId
      + "\\]\\.latitude");
  var longitudeField = $("#activity\\.otherLocations\\[" + otherSiteId
      + "\\]\\.longitude");

  // is valid value
  if (latitude == -1) {
    $(latitudeField).val('');
  } else {
    $(latitudeField).val(latitude);
  }

  if (longitude == -1) {
    $(longitudeField).val('');
  } else {
    $(longitudeField).val(longitude);
  }
}

/**
 * This function returns the country selected for the given other site
 * 
 * 
 */
function getOtherSiteCountry(otherSiteId) {
  countryId = $(
      "#locations_activity_otherLocations_" + otherSiteId + "__country").find(
      ":selected").text();
  return countryId;
}