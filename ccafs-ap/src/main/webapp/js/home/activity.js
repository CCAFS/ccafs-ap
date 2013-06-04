google.load('visualization', '1', {
  'packages' : [ 'geochart' ]
});
google.setOnLoadCallback(drawRegionsMap);

function drawRegionsMap() {
  var map;
  var activityID = $("#activityID").val();

  $.getJSON('activityLocations.do?id=' + activityID, function(data) {

    // ---------- Load countries ------------------
    var countries = data.countries;

    // setup the new map and its variables
    map = new google.visualization.DataTable();
    map.addRows(countries.length); // length gives us the number of results in our returned data
    map.addColumn('string', 'Country');

    if (countries.length == 0 && $("#countriesMessage").text() == "") {
      $("#countriesMessage").text($("#noCountriesMessage").val());
    }

    $.each(countries, function(i, country) {
      map.setValue(i, 0, country.id);
    });

    var options = {
      legend : 'none',
      backgroundColor : '#99B3CC',
      datalessRegionColor : '#CDDBB2'
    };

    var chart = new google.visualization.GeoChart(document
        .getElementById('country_locations'));
    chart.draw(map, options);

    // ---------- Load other locations ------------------
    // Vars used to adjust the position and zoom of the map
    var lat_min = 999;
    var lat_max = -999;
    var lng_min = 999;
    var lng_max = -999;

    // First load the map
    var mapOptions = {
      zoom : 1,
      center : new google.maps.LatLng(0, 0),
      mapTypeId : google.maps.MapTypeId.TERRAIN,
      disableDefaultUI : true,
      zoomControl : true
    };

    var map = new google.maps.Map(document.getElementById('other_locations'),
        mapOptions);

    var ccafsSiteImage = {
      url : 'https://dl.dropbox.com/u/68183213/ccafs_sites.png',
      // This marker is 20 pixels wide by 32 pixels tall.
      size : new google.maps.Size(20, 19),
      // The origin for this image is 0,0.
      origin : new google.maps.Point(0, 0),
      // The anchor for this image is the base of the flagpole at 0,32.
      anchor : new google.maps.Point(0, 32)
    };

    var otherSiteImage = {
      url : 'http://s.ytimg.com/yts/img/favicon-vfldLzJxy.ico',
      // This marker is 20 pixels wide by 32 pixels tall.
      size : new google.maps.Size(20, 32),
      // The origin for this image is 0,0.
      origin : new google.maps.Point(0, 0),
      // The anchor for this image is the base of the flagpole at 0,32.
      anchor : new google.maps.Point(0, 32)
    };

    var benchmarkSites = data.benchmarkSites;
    var otherSites = data.otherSites;

    if (benchmarkSites.length + otherSites.length == 0) {
      $("#otherLocationMessage").text($("#noOtherLocationsMessage").val());
    }

    // ------------- Benchmarks sites ------------------
    // setup the benchmark markers
    $.each(benchmarkSites, function(i, site) {
      var site_lat = site.benchmarkSite.latitude;
      var site_lng = site.benchmarkSite.longitud;
      var myLatLng = new google.maps.LatLng(site_lat, site_lng);
      var marker = new google.maps.Marker({
        position : myLatLng,
        map : map,
        icon : ccafsSiteImage,
        title : site.benchmarkSite.name
      });

      lat_min = (site_lat < lat_min) ? site_lat : lat_min;
      lat_max = (site_lat > lat_max) ? site_lat : lat_max;
      lng_min = (site_lng < lng_min) ? site_lng : lng_min;
      lng_max = (site_lng > lng_max) ? site_lng : lng_max;
    });

    // ------------- Other sites ------------------
    // setup the other markers
    baseIndex = countries.length + benchmarkSites.length;
    $.each(otherSites, function(i, site) {
      var myLatLng = new google.maps.LatLng(site.latitude, site.longitude);
      var marker = new google.maps.Marker({
        position : myLatLng,
        map : map,
        icon : otherSiteImage,
        title : site.details
      });

      lat_min = (site.latitude < lat_min) ? site.latitude : lat_min;
      lat_max = (site.latitude > lat_max) ? site.latitude : lat_max;
      lng_min = (site.longitude < lng_min) ? site.longitude : lng_min;
      lng_max = (site.longitude > lng_max) ? site.longitude : lng_max;
    });

    // Set the map center  
    map.setCenter(new google.maps.LatLng(((lat_max + lat_min) / 2.0),
        ((lng_max + lng_min) / 2.0)));

    // Set the zoom 
    map.fitBounds(new google.maps.LatLngBounds(
    //bottom left
    new google.maps.LatLng(lat_min, lng_min),
    //top right
    new google.maps.LatLng(lat_max, lng_max)));
  });
};