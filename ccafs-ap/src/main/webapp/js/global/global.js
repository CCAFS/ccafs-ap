// Global javascript must be here.
$(document).ready(function() {
  showNotificationMessages();
  
  function showNotificationMessages() {
    $('#generalMessages #messages').children("li").each(function(index) {
      $('#generalMessages').noty({
        type: $(this).attr("class"), 
        text: $(this).text()
      });
    });
  }
  
  /* Tooltips with JQuery UI*/
  $(document).tooltip({
    track: true
  });
  
});