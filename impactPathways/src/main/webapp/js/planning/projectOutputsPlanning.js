var limitWords = 50;
var $textAreas;
$(document).ready(function() {
  $textAreas = $("div#projectOutputs textarea");
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  $('#mogsTabs').tabs({
      show: {
          effect: "fadeIn",
          duration: 800
      },
      hide: {
          effect: "fadeOut",
          duration: 500
      }
  });
  applyWordCounter($textAreas, limitWords);
});
