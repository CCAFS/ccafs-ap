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

function isNumber(event){
  evt = (event) ? event : window.event;
  var charCode = (event.which) ? event.which : event.keyCode;
  if (charCode == 8 && (charCode < 37 || charCode > 40) && (charCode < 48 || charCode > 57) && (charCode < 96 || charCode > 105))
    return false;
  return true;
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
