var baseURL;
jQuery.fn.exists = function() {
  return this.length > 0;
};

// Global javascript must be here.
$(document).ready(function() {
  baseURL = $("#baseURL").val();
  showNotificationMessages();

  function showNotificationMessages() {
    $('#generalMessages #messages').children("li").each(function(index) {
      // Validate if the notification is a warning checking if the text contains --warn--
      var message = $(this).text();
      if(message.lastIndexOf("--warn--", 0) === 0) {
        message = message.replace("--warn--", " ");
        $('#generalMessages').noty({
            type: 'warning',
            text: message
        });

      } else {
        $('#generalMessages').noty({
            type: $(this).attr("class"),
            text: $(this).text()
        });
      }
    });
  }

  /* Tooltips with JQuery UI */
  $(this).tooltip({
    track: true
  });

  /**
   * Tick Box functions
   */
  var $tickBoxWp = $('.tickBox-wrapper input[type=checkbox]');

  $tickBoxWp.on('change', toggleInputs);
  $tickBoxWp.each(function() {
    var $toggle = $(this).closest('.tickBox-wrapper').find('.tickBox-toggle');
    if($(this).is(':checked')) {
      $toggle.show();
    } else {
      $toggle.hide();
    }
  });

  function toggleInputs(e) {
    $(this).parent().parent().parent().find('.tickBox-toggle').slideToggle($(e.target).is(':checked'));
  }

});

/**
 * Validate fields length when click to any button
 */

function validateEvent(button,fields) {
  var errorClass = 'fieldError';
  $(button).on('click', function(e) {
    $.each(fields, function(i,val) {
      $(val).each(function() {
        $(this).removeClass(errorClass);
        if(!validateField($(this))) {
          e.preventDefault();
          $(this).addClass(errorClass);
        }
      });
    });
  });
}

function validateField($input) {
  var valid = ($input.val().length > 0) ? true : false;
  return valid;
}

/* Add a char counter to a specific text area */
function applyCharCounter($textArea,charCount) {
  $textArea.parent().append("<p class='charCount'>(<span>" + charCount + "</span> characters remaining)</p>");
  $textArea.next(".charCount").find("span").text(charCount - $textArea.val().length);
  $textArea.on("keyup", function(event) {
    if($(event.target).val().length > charCount) {
      $(event.target).val($(event.target).val().substr(0, charCount));
    }
    $(event.target).next(".charCount").find("span").text(charCount - $(event.target).val().length);
  });
  $textArea.trigger("keyup");
}

/* Add a word counter to a specific text area */
function applyWordCounter($textArea,wordCount) {
  $textArea.parent().append("<p class='charCount'>(<span>" + wordCount + "</span> words remaining)</p>");
  $textArea.next(".charCount").find("span").text(wordCount - word_count($textArea));
  $textArea.on("keyup", function(event) {
    var $charCount = $(event.target).next(".charCount");
    if(word_count($(event.target)) > wordCount) {
      $(event.target).val($(event.target).val().slice(0, -2));
      $(event.target).addClass('fieldError');
      $charCount.addClass('fieldError');
    } else {
      $(event.target).removeClass('fieldError');
      $charCount.removeClass('fieldError');
    }
    // Set count value
    $charCount.find("span").text(wordCount - word_count(event.target));

  });
  $textArea.trigger("keyup");
}

function word_count(field) {
  var value = $(field).val();
  if(typeof value === "undefined") {
    return 0;
  } else {
    var matches = $(field).val().match(/\b/g);
    return number = (matches) ? matches.length / 2 : 0;
  }

}

/**
 * Functions for selects
 */
function setOption(val,name) {
  return "<option value='" + val + "'>" + name + "</option>";
}

/**
 * Escape HTML text
 */

function escapeHtml(text) {
  var map = {
      '&': '&amp;',
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#039;'
  };
  return text.replace(/[&<>"']/g, function(m) {
    return map[m];
  });
}
