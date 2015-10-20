$(document).ready(init);

function init() {
  console.log('P&R Help');
  attachEvents();
}

function attachEvents() {
  // Attach events here
}

function displaySection(section){
  $(".helpContent").hide();
  $("#"+section).show(1000);
}
