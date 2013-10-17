/*
 *  This function takes the links whit popup class and 
 *  add a click event. That event takes the href and open
 *  it in a popUp window 
 * 
 *  This method must be called in ready function
 *  
 * */

function popups() {
  $("a.popup").click(function(event) {
    event.preventDefault();
    var options = "width=700,height=500,resizable=1,scrollbars=1,location=0";
    nueva = window.open(this.href, '_blank', options);
  });
}

function isNumber(event) {
  var charCode = event.which;
  if (charCode > 31 && (charCode < 48 || charCode > 57))
     return false;

  return true;
}