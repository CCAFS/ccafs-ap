$(document).ready(function() {
  $("input[name='reportTypeSelected']").on("change", function(){
    $(".reportOptions").fadeIn('slow');
  });
  
  $("#allActivitiesByYear").on("change", function(){
    if($(this).is(':checked')){
      $("#activities_reportYear").attr("disabled", false);
      $("#activityID").attr("disabled", true);
      
      $("#activities_reportYear").focus();
    }
  });
  
  $("#activityIdentifier").on("change", function(){
    if($(this).is(':checked')){
      $("#activities_reportYear").attr("disabled", true);
      $("#activityID").attr("disabled", false);
      
      $("#activityID").focus();
    }
  });
  
  $("#allActivities").on("change", function(){
    if($(this).is(':checked')){
      $("#activities_reportYear").attr("disabled", true);
      $("#activityID").attr("disabled", true);
    }
  });
  
  
});