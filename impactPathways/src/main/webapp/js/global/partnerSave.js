$(document).ready(function() {
  
  if($("#messageSent").val() == "true"){
    var message = $("#message\\.success").val();
    alert(message);
    window.close();
  }
  
});
