/*
 *  This function takes the links whit popup class and 
 *  add a click event. That event takes the href and open
 *  it in a popUp window 
 * 
 *  This method must be called in ready function
 *  
 * */

function popups(){
  $("a.popup").click(function(event){
    event.preventDefault();
    var options = "width=700,height=500,resizable=1,scrollbars=1,location=0";
    nueva = window.open(this.href, '_blank', options);
  });
}

function isNumber(e){
  if ($.inArray(e.keyCode, [
      46, 8, 9, 27, 13, 110, 190
  ]) !== -1 ||
  // Allow: Ctrl+A
  (e.keyCode == 65 && e.ctrlKey === true) ||
  // Allow: home, end, left, right
  (e.keyCode >= 35 && e.keyCode <= 39)) {
    // let it happen, don't do anything
    return;
  }
  // Ensure that it is a number and stop the keypress
  if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
    e.preventDefault();
  }
}

function printOut(){
  $("form input, form textarea, form select, form button").each(function(i,input){
    if ($(input).attr("name")) {
      console.log("> " + $(input).attr("name") + ": " + $(input).val() + " (" + input.tagName + ")");
    }
  });
}

function setCurrencyFormat(stringNumber){
  return parseFloat(stringNumber, 10).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
}

function removeCurrencyFormat(stringNumber){
  return parseFloat(stringNumber.replace(/,/g, ''));
}
