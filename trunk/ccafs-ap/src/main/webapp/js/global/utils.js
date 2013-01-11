/*
 *  This function takes the links whit popu class and 
 *  add a click event. That event takes the href and open
 *  it in a popUp window 
 * 
 *  This method must be called in ready function
 *  
 * */

function popups() {
  $("a.popup")
      .click(
          function(event) {
            event.preventDefault();
            var options = "width=600,height=450scrollTo,resizable=1,scrollbars=1,location=0";
            nueva = window.open(this.href, '', options);
          });
}