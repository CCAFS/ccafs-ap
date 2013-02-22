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
  $(this).tooltip({
    track: true
  });
  
});

/* Add a char counter to a specific text area */
function applyCharCounter($textArea, charCount) {
  $textArea.parent().append("<p class='charCount'>(<span>"+charCount+"</span> characters remaining)</p>");
  $textArea.next(".charCount").find("span").text(charCount-$textArea.val().length);
  $textArea.on("keyup", function(event) {
    if($(event.target).val().length > charCount) {
      $(event.target).val($(event.target).val().substr(0,charCount));
    }
    $(event.target).next(".charCount").find("span").text(charCount-$(event.target).val().length);
  });
}