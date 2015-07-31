var limitWords = 50;
var $textAreas;
$(document).ready(function() {
  $textAreas = $("div#projectOutputs textarea");
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  applyWordCounter($textAreas, limitWords);
  $textAreas.autoGrow();
});
