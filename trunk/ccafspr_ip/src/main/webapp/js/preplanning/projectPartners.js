$(document).ready(function(){
  attachEvents();
  if (!$("div.projectPartner").length) {
    $("a.addProjectPartner").trigger("click");
  } else {
    // Activate the chosen plugin to the existing partners
    addChosen();
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
  var $parent = $(e.target).parent().parent();
  $parent.hide("slow", function(){
    $parent.remove();
    setProjectPartnersIndexes();
  });
}

function addPartnerEvent(e){
  e.preventDefault();
  var $newElement = $("#projectPartnerTemplate").clone(true).removeAttr("id").addClass("projectPartner");
  $(e.target).parent().before($newElement);
  $newElement.show("slow");
  
  // Activate the chosen plugin
  $newElement.find("select[name$='partner']").chosen({
  no_results_text : $("#noResultText").val(),
  search_contains : true
  });
  $newElement.find(".partnerTypes").chosen({
  allow_single_deselect : true,
  search_contains : true
  });
  $newElement.find(".countryList").chosen({
  allow_single_deselect : true,
  search_contains : true
  });
  setProjectPartnersIndexes();
}

function setProjectPartnersIndexes(){
  $("div.projectPartner").each(function(index,element){
    var elementName = "project.projectPartners[" + index + "].";
    $(element).attr("id", "projectPartner-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[id$='partnerIndex']").html(index + 1);
    $(element).find("[id$='id']").attr("name", elementName + "id");
    $(element).find("[id$='partner']").attr("name", elementName + "partner");
    $(element).find("[id$='contactName']").attr("name", elementName + "contactName");
    $(element).find("[id$='contactEmail']").attr("name", elementName + "contactEmail");
    $(element).find("[id$='responsabilities']").attr("name", elementName + "responsabilities");
  });
}

// Activate the chosen plugin to the countries, partner types and
// partners lists.
function addChosen(){
  $("form select[name$='partner']").each(function(){
    // Check if its not the template partner field
    if ($(this).attr("name") != '__partner') {
      $(this).chosen({
      no_results_text : $("#noResultText").val(),
      search_contains : true
      });
    }
  });
  
  $("form .partnerTypes").each(function(){
    // Check if its not the template partner types field
    if ($(this).attr("id") != 'partners_partnerTypeList') {
      $(this).chosen({
      allow_single_deselect : true,
      search_contains : true
      });
    }
  });
  
  $("form .countryList").each(function(){
    // Check if its not the template countries field
    if ($(this).attr("id") != 'partners_countryList') {
      $(this).chosen({
      allow_single_deselect : true,
      search_contains : true
      });
    }
  });
}
