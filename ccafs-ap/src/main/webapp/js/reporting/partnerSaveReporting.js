$(document).ready(function() {
  if($("[name='activityID']").val() == 0) {
    alert("The request has been successfully sent");
    window.close();
  };
});
