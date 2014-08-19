jQuery.fn.exists = function(){
  return this.length > 0;
};

// Global javascript must be here.
$(document).ready(function(){
  showNotificationMessages();
  
  function showNotificationMessages(){
    $('#generalMessages #messages').children("li").each(function(index){
      // Validate if the notification is a warning checking if the text contains --warn--
      var message = $(this).text();
      if (message.lastIndexOf("--warn--", 0) === 0) {
        message = message.replace("--warn--", " ");
        $('#generalMessages').noty({
          type : 'warning',
          text : message
        });
        
      } else {
        $('#generalMessages').noty({
          type : $(this).attr("class"),
          text : $(this).text()
        });
      }
    });
  }
  
  /* Tooltips with JQuery UI */
  $(this).tooltip({
    track : true
  });
  
});

/* Add a char counter to a specific text area */
function applyCharCounter($textArea,charCount){
  $textArea.parent().append("<p class='charCount'>(<span>" + charCount + "</span> characters remaining)</p>");
  $textArea.next(".charCount").find("span").text(charCount - $textArea.val().length);
  $textArea.on("keyup", function(event){
    if ($(event.target).val().length > charCount) {
      $(event.target).val($(event.target).val().substr(0, charCount));
    }
    $(event.target).next(".charCount").find("span").text(charCount - $(event.target).val().length);
  });
}

/* Add a word counter to a specific text area */
function applyWordCounter($textArea,wordCount){
  $textArea.parent().append("<p class='charCount'>(<span>" + wordCount + "</span> words remaining)</p>");
  $textArea.next(".charCount").find("span").text(wordCount - word_count($textArea));
  $textArea.on("keyup", function(event){
    if (word_count($textArea) > wordCount) {
      $(event.target).val($(event.target).val().slice(0, -2));
      $(event.target).next(".charCount").find("span").text(wordCount - word_count(event.target));
    } else {
      $(event.target).next(".charCount").find("span").text(wordCount - word_count(event.target));
    }
    
  });
}

function word_count(field){
  var matches = $(field).val().match(/\b/g);
  return number = (matches) ? matches.length / 2 : 0;
}