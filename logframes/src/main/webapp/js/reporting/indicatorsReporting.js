$(document).ready(function() {  
  $( "#accordion" ).tabs();
  
  $(".toggleDescription").on("click", function(evt){
    var element = evt.target;
    $(element).parent().prev().toggle();
    
    if($(element).hasClass("show")){
      $(element).removeClass("show");
      $(element).addClass("hide");
      
      $(element).text($("#hideDescriptionText").val());
    }else{
      $(element).removeClass("hide");
      $(element).addClass("show");
      
      $(element).text($("#showDescriptionText").val());
    }
  });
});

