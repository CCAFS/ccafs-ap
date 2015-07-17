$(document).ready(function() {
  if($("[name='activityID']").val() == 0) {
    var message = $("#message\\.success").val();
    alert(message);
    window.close();
  };
});
