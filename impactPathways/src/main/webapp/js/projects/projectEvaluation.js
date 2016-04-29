$(document).ready(init);

function init() {
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
  setScoresColor();
  $('input.hover-star').rating();
  attachEvents();
}

function attachEvents() {
  $('[class*="control-"]').on('click', function() {
    console.log();
    $("#" + $(this).attr('class').split('-')[1]).slideToggle();
  });

  // $('input.hover-star[name$=p1]:checked').val()
  $('input.hover-star').on('change', function() {
    var $rank = $(this).parents('tr');
    var totalScore = 0;
    $rank.find('td').each(function(i,element) {
      var value = $(element).find('input.hover-star:checked').val() || 0;
      var weight = $(element).find('span.weight').text() || 0;
      totalScore += value * (weight / 100);
    });
    totalScore = totalScore/2;
    $(this).parents('.evaluationBlock').find('p.totalScore').text(totalScore.toFixed(2));
    setScoresColor();
  });
}

/** Set colors to project scores * */

function setScoresColor() {
  $('p.totalScore').each(function(i,element) {
    var score = parseFloat($(element).text());
    $(element).removeClass('green yellow red')
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