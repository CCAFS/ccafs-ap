$(document).ready(
    function() {
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
      
      $("#activity\\.global").on(
          "change",
          function() {
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
              $("[id ^= countriesForRegion-]").each(
                  function() {
                    $(this).find("input").attr("checked", false);
                    $(this).find("[id ^= locations_activity_countries-]").val(
                        '').trigger('liszt:updated');
                    $(this).fadeOut();
                  });

              // BS locations
              $("#bsLocations").hide();
              $(".checkboxGroup [id ^= 'activity.bsLocations-']").each(
                  function() {
                    $(this).next().remove();
                    $(this).remove();
                  });

              // Other sites
              $("#otherSites").find(".otherSite").each(function(index) {
                if (index == 0) {
                  $(this).find("[id$=latitude]").val("");
                  $(this).find("[id$=longitude]").val("");
                  $(this).find("[id$=details]").val("");
                } else {
                  $(this).remove();
                }
              });

            } else {
              $("#regionsLocations").show("slow");

              // Countries locations
              $("[id ^= countriesForRegion-]").each(
                  function() {
                    $(this).find("[id ^= locations_activity_countries-]").attr(
                        'disabled', false);
                    $(this).find("[id ^= locations_activity_countries-]")
                        .trigger('liszt:updated');
                  });

              $("#countryLocations").show("slow");
              $("#otherSites").show("slow");
            }
          });

      $("[id^=region-]").on("change", showCountriesByRegion);

      $("[id^=allCountriesForRegion-]").on(
          "change",
          function(event) {
            event.preventDefault();
            // Getting the id.
            var regionId = $(event.target).attr("id").split("-")[1];
            // Enable or unable the corresponding select
            if ($(event.target).attr('checked') == "checked") {
              // Show ccafs sites
              showCcafsSitesByRegion(parseInt(regionId) + 1, "show");
              
              // Hide the countries field
              $("select#locations_activity_countries-" + regionId).attr(
                  'disabled', true);
              $("select#locations_activity_countries-" + regionId).val('');
              $("select#locations_activity_countries-" + regionId).trigger(
                  "liszt:updated");
            } else {
              // Delete values from countries select
              $("select#locations_activity_countries-" + regionId).val('');
              // Show the countries field
              $("select#locations_activity_countries-" + regionId).attr(
                  'disabled', false);
              $("select#locations_activity_countries-" + regionId).trigger(
                  "liszt:updated");
              showCcafsSitesByRegion(parseInt(regionId) + 1, "hide");
            }
          });
      
      $(".countries").each(function(){
        $(this).attr('data-placeholder', $("#countriesSelectDefault").val());        
      });

      popups();
      changeBsLocationsIDs();
      addChosen();
    });

//Activate the chosen plugin to the countries inputs
function addChosen() {
  // Country locations
  $("#countryLocations .countries").each(function(index) {
      // Add the index to the id attribute to prevent duplicate id
      $(this).attr("id", $(this).attr("id") + "-" + index);
      $(this).chosen();
      $(this).chosen().change(showCcafsSitesByCountry);
  });
  
  // Other sites countries
  $("#otherSites .countries").each(function(index) {
    // Add the index to the id attribute to prevent duplicate id
    $(this).attr("id", $(this).attr("id") + "-" + index);
    $(this).chosen();
  });
}

/**
 * This function changes the benchmark sites id (checkbox) to run the 
 * Toogle effect. Benchmark site identifier must have the country identifier 
 */
function changeBsLocationsIDs(){
  var sitesByRegionLink = '../json/sitesByRegion.do?regionID='; 
  var siteIndex = 1;
  
  $("[name = 'activity\\.countries']").each(function(index){
    if($(this).val() != null){
      regionID = index+1;
      $.getJSON( sitesByRegionLink + regionID, function(data) {
        var ccafsSites = data.benchmarkSites;

        for(var c=0; c < ccafsSites.length; c++){
          $("[name = 'activity\\.bsLocations']").each(function(index){
            if($(this).val() == ccafsSites[c].id){
              $(this).attr("id", "activity.bsLocations-" + ccafsSites[c].country.id + "-" + siteIndex );
              siteIndex++;
            }             
          });
        }
      });
    }
  });
}

function drawRegionsMap() {

  // First load the map
  var mapOptions = {
    zoom : 8,
    center : new google.maps.LatLng(-34.397, 150.644),
    mapTypeId : google.maps.MapTypeId.TERRAIN
  };
  map = new google.maps.Map(document.getElementById('other_locations'),
      mapOptions);
};


function getOtherSitesCoordinates(otherSiteID){
  var location = new Array();
  
  var latitude = $("#activity\\.otherLocations\\[" + otherSiteID + "\\]\\.latitude").val();
  var longitude = $("#activity\\.otherLocations\\[" + otherSiteID + "\\]\\.longitude").val();
  
  if(!isNaN(parseFloat(latitude)) && !isNaN(parseFloat(longitude))){
    location["lat"] = parseFloat(latitude);
    location["lng"] = parseFloat(longitude);
  }else{
    location = null;
  }
  
  return location;
}


/**
 * This function returns the country selected for the given other site
 */
function getOtherSiteCountry(otherSiteId) {
  countryId = $(
      "#locations_activity_otherLocations_" + otherSiteId + "__country").find(
      ":selected").text();
  return countryId;
}


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
        $(this).find(".popup").attr("id", '');
        $(this).find(".popup").attr("href",
            $("#geoLocationLink").attr('href') + index);
      });
}


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
 * Checks what country was selected and loads the corresponding CCAFS sites if
 * there is any
 * 
 */

function showCcafsSitesByCountry(event, data) {

  if (data.selected != null) {
    // The country was selected
    country = data.selected;

    $.getJSON('../json/sitesByCountry.do?countryID='
        + country, function(data) {
      var ccafsSites = data.benchmarkSites;
      var inputsHtml = "";
      for ( var c = 0; c < ccafsSites.length; c++) {
        inputsHtml += "<input id='activity.bsLocations-"
            + ccafsSites[c].country.id + "-" + c
            + "' class='checkbox' type='checkbox' value='" + ccafsSites[c].id
            + "' name='activity.bsLocations'> ";

        inputsHtml += "<label class='checkboxLabel' for='activity.bsLocations-"
            + ccafsSites[c].country.id + "-1'> " + ccafsSites[c].name
            + "</label> ";
      }

      $(".benchmarkSites .checkboxGroup").append(inputsHtml);
      $("#bsLocations").fadeIn();
    });

  } else {
    // The country was un-checked
    country = data.deselected;

    $(".checkboxGroup [id ^= 'activity.bsLocations-']").each(function() {
      var countryID = $(this).attr("id").split("-")[1];
      countryID = countryID.split("-")[0];

      if (countryID == country) {
        $(this).next().remove();
        $(this).remove();
      }
    });

    if ($(".checkboxGroup [id ^= 'activity.bsLocations-']").length == 0) {
      $("#bsLocations").fadeOut();
    }
  }

}


function showCcafsSitesByRegion(regionID, action) {
  var data = new Array();
  var countriesSelected = $("#locations_activity_countries-" + (regionID-1)).val();

  $.getJSON('../json/sitesByRegion.do?regionID='
      + regionID, function(data) {

    var ccafsSites = data.benchmarkSites;
    for ( var c = 0; c < ccafsSites.length; c++) {
      if (action == "show") {
        var countrySelected = false;
        var inputsHtml = "";
        
        // If the country is selected don't show the corresponding sites
        // again
        if(countriesSelected != null){
          for(var i = 0; i < countriesSelected.length; i++){
            if(ccafsSites[c].country.id == countriesSelected[i]){
              countrySelected = true;
              break;
            }
          }
          
          if(countrySelected){
            continue;
          }
        }
        
        inputsHtml += "<input id='activity.bsLocations-"
            + ccafsSites[c].country.id + "-" + c
            + "' class='checkbox' type='checkbox' value='" + ccafsSites[c].id
            + "' name='activity.bsLocations'> ";

        inputsHtml += "<label class='checkboxLabel' for='activity.bsLocations-"
            + ccafsSites[c].country.id + "-" + c + "'> " + ccafsSites[c].name
            + "</label> ";

        $(".benchmarkSites .checkboxGroup").append(inputsHtml);
        $("#bsLocations").fadeIn();
      } else {
        $(".checkboxGroup [id ^= 'activity.bsLocations-" + ccafsSites[c].country.id + "']").each(function() {
            $(this).next().remove();
            $(this).remove();
        });

        if ($(".checkboxGroup [id ^= 'activity.bsLocations-']").length == 0) {
          $("#bsLocations").fadeOut();
        }
      }
    }

  });
}


/**
 * Make a request to show the countries that belongs to the region selected.
 * 
 */
function showCountriesByRegion(event) {
  regionInput = event.target;
  regionID = $(regionInput).attr("value");

  if ($(regionInput).attr("checked") == "checked") {

    // Display div block
    $("#countriesForRegion-" + (regionID)).fadeIn();

  } else {
    // If the user un-check the input hide the field and reset the values
    $("#countriesForRegion-" + (regionID)).fadeOut();
    $("#allCountriesForRegion-" + (regionID - 1)).attr('checked', false);
    $("select#locations_activity_countries-" + (regionID - 1)).attr('disabled',
        false);
    $("select#locations_activity_countries-" + (regionID - 1)).val('').trigger(
        'liszt:updated');

    $("#bsLocations").hide();
    $(".checkboxGroup [id ^= 'activity.bsLocations-']").each(function() {
      $(this).next().remove();
      $(this).remove();
    });
  }
}






