$(document).ready(function(){
  init();
});
function init(){
  console.log("init projectBudget.js");
  //
  $("#budgetTables").tabs({
    show : {
    effect : "slide",
    direction : "up",
    duration : 500
    }
  });
}
