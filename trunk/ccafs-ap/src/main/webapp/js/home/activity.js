google.load('visualization', '1', {
  'packages' : [ 'geochart' ]
});
google.setOnLoadCallback(drawRegionsMap);

function drawRegionsMap() {
  var map;
  var activityID = $("#activityID").val();

  $.getJSON('activityLocations.do?id=' + activityID, function(data) {
    var countries = data.countries;

    // setup the new map and its variables
    map = new google.visualization.DataTable();
    map.addRows(countries.length); // length gives us the number of results in our returned data
    map.addColumn('string', 'Country');
    map.addColumn('number', 'color-intensity');

    // now we need to build the map data, loop over each result
    $.each(countries, function(i, country) {
      map.setValue(i, 0, country.id);
      map.setValue(i, 1, i);
    });

    var options = {
      legend : 'none',
      backgroundColor : '#D5F0FD',
      datalessRegionColor : '#EFDE71'
    };

    var chart = new google.visualization.GeoChart(document
        .getElementById('country_locations'));
    chart.draw(map, options);

  });

};
//F4D923