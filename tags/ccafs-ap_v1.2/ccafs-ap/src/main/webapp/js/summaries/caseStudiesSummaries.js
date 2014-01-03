$(document).ready(function() {
  
  $("#caseStudies_countriesSelected").attr("data-placeholder", $("#countryListDefault").val());
  $("#caseStudies_countriesSelected").chosen({search_contains:true});
  $("#caseStudies_leadersSelected").chosen({search_contains:true});
  $("#caseStudies_yearSelected").chosen();
});