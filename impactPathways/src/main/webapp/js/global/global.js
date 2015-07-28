var baseURL;
var formBefore;
jQuery.fn.exists = function() {
  return this.length > 0;
};

// Global javascript must be here.
$(document).ready(function() {
  baseURL = $("#baseURL").val();
  showNotificationMessages();

  // hash url animation
  setTimeout(function() {
    $(window.location.hash).addClass('animated pulse');
  }, 300);

  function showNotificationMessages() {
    $('#generalMessages #messages').children("li").each(function(index) {
      // Validate if the notification is a warning checking if the text contains --warn--
      var message = $(this).text();
      var messageType;
      if(message.lastIndexOf("--warn--", 0) === 0) {
        message = message.replace("--warn--", " ");
        messageType = 'warning';
      } else {
        messageType = $(this).attr("class");
      }

      $('#generalMessages').noty({
          theme: 'relax',
          layout: 'top',
          theme: 'relax', // or 'relax'
          type: messageType,
          text: message, // can be html or string
          dismissQueue: true, // If you want to use queue feature set this true
          animation: {
              open: 'animated flipInX', // Animate.css class names
              close: 'animated flipInX' // Animate.css class names
          },
          timeout: 10000, // delay for closing event. Set false for sticky notifications
          force: false, // adds notification to the beginning of queue when set to true
          modal: false,
          maxVisible: 5, // you can set max visible notification for dismissQueue true option,
          killer: false, // for close all notifications before show
          closeWith: [
            'click'
          ]

      });
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

  // Generating hash from form information
  setFormHash();
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
        if(isChanged()) {
          if(!validateField($(this))) {
            e.preventDefault();
            $(this).addClass(errorClass);
            noty({
                text: 'The ' + val.replace("#", "") + ' field need to be filled',
                layout: 'bottomRight',
                theme: 'relax',
                timeout: 5000,
                animation: {
                    open: 'animated bounceInRight', // Animate.css class names
                    close: 'animated bounceOutRight' // Animate.css class names
                },
                type: 'error'
            });
          }
        } else {
          e.preventDefault();
          noty({
              text: 'Nothing changed',
              layout: 'bottomRight',
              theme: 'relax',
              timeout: 2500,
              animation: {
                  open: 'animated bounceInRight', // Animate.css class names
                  close: 'animated bounceOutRight' // Animate.css class names
              },
              type: 'alert'
          });
          /*
           * var $msj = $('<p class="msj">Nothing changed</p>'); $(button).parent().after($msj.hide());
           * $msj.fadeIn(1000, function() { $msj.fadeOut(1000, "easeInExpo", function() { $(this).remove(); }); });
           */
        }
      });
    });
  });
}

function isChanged() {
  return(formBefore != getFormHash());
}

function setFormHash() {
  formBefore = getFormHash();
}

function getFormHash() {
  return getHash($('form [id!="justification"]').serialize());
}

function validateField($input) {
  var valid = ($input.val().length > 0) ? true : false;
  return valid;
}

function getHash(str) {
  var hash = 0, i, chr, len;
  if(str.length == 0) {
    return hash;
  }
  for(i = 0, len = str.length; i < len; i++) {
    chr = str.charCodeAt(i);
    hash = ((hash << 5) - hash) + chr;
    hash |= 0; // Convert to 32bit integer
  }
  return hash;
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
