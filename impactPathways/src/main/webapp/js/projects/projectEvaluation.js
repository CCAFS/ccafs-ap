$(document).ready(init);

function init() {
  setScoresColor();
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
      // 3.6 - 5.0 (Green)
      $(element).addClass('green');
    } else if((score > 2) && (score <= 3.5)) {
      // 2.1 - 3.5 (Yellow)
      $(element).addClass('yellow');
    } else if(score <= 2) {
      // 0,5 - 2.0 (Red)
      $(element).addClass('red');
    }
  });
}