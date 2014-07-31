$(document).ready(function(){
  attachEvents();
  if (!$("div.projectPartner").length) {
    $("a.addProjectPartner").trigger("click");
  }
});

function attachEvents(){
  // Partners Events
  $("a.addProjectPartner").click(addPartnerEvent);
  $("a.removePartner").click(removePartnerEvent);
  
  // When Partner Type change
  $("select.partnerTypes, select.countryList").change(updateOrganizationsList);
}

function updateOrganizationsList(e){
  var $parent = $(e.target).parent().parent().parent().parent();
  var partnerTypes = $parent.find("select.partnerTypes").find('option:selected').val();
  var countryList = $parent.find("select.countryList").find('option:selected').val();
  console.log("GET -> json/partnersByFilter.do?partnerTypeID=" + partnerTypes + "&countryID=" + countryList);
  
}

// Partner Events
function removePartnerEvent(e){
  e.preventDefault();
  $(e.target).parent().parent().hide("slow", function(){
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
  $("div.projectPartner").each(function(index,element){
    var elementName = "project.projectPartners[" + index + "].";
    $(element).attr("id", "projectPartner-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[id$='id']").attr("name", elementName + "id");
    $(element).find("[id$='partner']").attr("name", elementName + "partner");
    $(element).find("[id$='contactName']").attr("name", elementName + "contactName");
    $(element).find("[id$='contactEmail']").attr("name", elementName + "contactEmail");
    $(element).find("[id$='responsabilities']").attr("name", elementName + "responsabilities");
  });
}
