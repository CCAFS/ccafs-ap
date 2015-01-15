$(document).ready(function(){
  console.log("rank");
  
  $(this).tooltip("destroy");
  
  $('.hover-star').rating({
    cancel : 'Cancel',
    cancelValue : '0',
    focus : function(value,link){
      var tip = $('#hover-test');
      tip[0].data = tip[0].data || tip.html();
      tip.html(link.title || 'value: ' + value);
    },
    blur : function(value,link){
      var tip = $('#hover-test');
      $('#hover-test').html(tip[0].data || '');
    }
  });
});
