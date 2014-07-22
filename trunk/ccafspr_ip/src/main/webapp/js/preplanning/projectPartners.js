$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  // Partners Events
  $("a.addProjectPartner").click(addPartnerEvent);  
  $("a.removePartner").click(removePartnerEvent);  
}


// Partner Events
function removePartnerEvent(e){
    e.preventDefault(); 
    $(e.target).parent().parent().hide("slow", function (){
	this.remove();
	setProjectPartnersIndexes();
    });
}

function addPartnerEvent(e){
    e.preventDefault(); 
    var $newElement = $("#projectPartnerTemplate").clone(true).removeAttr("id").addClass("projectPartner");  
    $(e.target).parent().before($newElement); 
    $newElement.show("slow");
    setProjectPartnersIndexes();
}

function setProjectPartnersIndexes(){
    $("div.projectPartner").each(function(index, element){ 
        var elementName = "project.projectPartners[" + index + "]."; 
        $(element).attr("id","projectPartner-"+index);
        // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
        $(element).find("[id$='id']").attr("name", elementName + "id");
        $(element).find("[id$='partnerId']").attr("name", elementName + "partnerId");
        $(element).find("[id$='contactName']").attr("name", elementName + "contactName");
        $(element).find("[id$='contactEmail']").attr("name", elementName + "contactEmail"); 
    }); 
}