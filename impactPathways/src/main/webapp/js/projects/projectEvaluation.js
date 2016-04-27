$(document).ready(init);

function init() {
  setScoresColor();
  $('input.hover-star').rating();
  attachEvents();
}

function attachEvents() {
  $('[class*="control-"]').on('click', function() {
    console.log();
    $("#" + $(this).attr('class').split('-')[1]).slideToggle();
  });
}

/** Set colors to project scores * */

function setScoresColor() {
  $('p.totalScore').each(function(i,element) {
    var score = parseFloat($(element).text());
    if(score > 3.5) {
      $(element).addClass('green'); // 3.6 - 5.0 (Green)
    } else if((score > 2) && (score <= 3.5)) {
      $(element).addClass('yellow'); // 2.1 - 3.5 (Yellow)
    } else if(score <= 2) {
      $(element).addClass('red'); // 0,5 - 2.0 (Red)
    }
  });
}