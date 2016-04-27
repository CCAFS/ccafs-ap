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
    var p1 = $($rank.find('td')[0]).find('input.hover-star:checked').val() || 0;
    var p2 = $($rank.find('td')[1]).find('input.hover-star:checked').val() || 0;
    var p3 = $($rank.find('td')[2]).find('input.hover-star:checked').val() || 0;
    var p4 = $($rank.find('td')[3]).find('input.hover-star:checked').val() || 0;
    var p5 = $($rank.find('td')[4]).find('input.hover-star:checked').val() || 0;

    var totalScore = (p1 * 0.2) + (p2 * 0.35) + (p3 * 0.15) + (p4 * 0.15) + (p5 * 0.15)
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