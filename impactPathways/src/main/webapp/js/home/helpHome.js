$(document).ready(init);

$(function() {
  $( "#tabs" ).tabs();
});
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

/*
var buffer = 20; //scroll bar buffer
var iframe = document.getElementById('iframeDMSP');

function pageY(elem) {
    return elem.offsetParent ? (elem.offsetTop + pageY(elem.offsetParent)) : elem.offsetTop;
}
document.getElementById('iframeDMSP').onload = function() {
  alert("---");
  var height = document.documentElement.clientHeight;
  height -= pageY(document.getElementById('iframeDMSP'))+ buffer ;
  height = (height < 0) ? 0 : height;
  document.getElementById('iframeDMSP').style.height = height + 'px';
};
window.onresize = resizeIframe;*/
