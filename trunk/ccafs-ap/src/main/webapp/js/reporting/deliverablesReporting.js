$(document).ready(function() {
  $('.addDeliverable').click(function(event) {
    event.preventDefault();
    var num = $('.cloned').length; // how many "duplicatable" input fields we currently have
    var newNum = new Number(num + 1); // the numeric ID of the new input field being added

    // create the new element via clone() 
    var newElem = $('#reportingDeliverable').clone();

    // Add it's class and ID
    newElem.attr('id', 'reportingDeliverable' + newNum);
    newElem.attr('class', 'cloned');

    $(newElem).hide();

    // insert the new element after the last "duplicatable" input field
    $('#reportingDeliverable' + num).after(newElem);

    $(newElem).show("slow");
    // enable the "remove" button
    //  $('#btnDel').attr('disabled', '');
  });

  $('.removeDeliverable').click(function(event) {
    event.preventDefault();
    var num = $('.cloned').length; // how many "duplicatable" input fields we currently have

    $(event.target).parent().parent().hide("slow", function() {
      $(this).remove(); // remove the element
    });

  });

});
