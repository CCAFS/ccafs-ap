var lWordsLessons = 100;
var limitWords = 50;
var $textAreas;
$(document).ready(function() {
  $textAreas = $("div#projectOutputs textarea");
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  $('#mogsTabs').tabs({
      active: $('#indexTabCurrentYear').val(),
      show: {
          effect: "fadeIn",
          duration: 500
      },
      hide: {
          effect: "fadeOut",
          duration: 300
      }
  });
  applyWordCounter($textAreas, limitWords);
  applyWordCounter($("#lessons textarea"), lWordsLessons);
});
