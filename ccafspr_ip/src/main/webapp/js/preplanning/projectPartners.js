$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  // Partners Events
  $("a.addProjectPartner").click(addPartnerEvent);  
  $("a.removePartner").click(removePartnerEvent);  
}

function removePartnerEvent(e){
    e.preventDefault();
    //$parent = $(e.target).parent().parent();
    $(e.target).parent().parent().hide("slow", function (){
	this.remove();
    });
}

function addPartnerEvent(e){
    e.preventDefault(); 
}