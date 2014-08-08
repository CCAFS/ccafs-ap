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
  
  // Partners filters
  $(".filters-link").click(function(event){
    var $filterContent = $(event.target).next();
    if ($filterContent.is(":visible"))
      updateOrganizationsList(event);
    $filterContent.slideToggle();
  });
  
  // When Partner Type change
  $("select.partnerTypes, select.countryList").change(updateOrganizationsList);
}

function updateOrganizationsList(e){
  var eventType = $(e.target).attr("class");
  var $parent = $(e.target).parent().parent().parent().parent().parent();
  if (eventType == "filters-link")
    $parent = $(e.target).parent();
  var $selectInstitutions = $parent.find("select[name$='partner']");
  var partnerTypes = $parent.find("select.partnerTypes").find('option:selected').val();
  var countryList = $parent.find("select.countryList").find('option:selected').val();
  var source = "../json/institutionsByTypeAndCountry.do?institutionTypeID=" + partnerTypes + "&countryID=" + countryList;
  if (eventType == "filters-link")
    source = "../json/institutionsByTypeAndCountry.do";
  var institutionsList = "";
  $.getJSON(source, function(data){
    $.each(data.institutions, function(index,institution){
      institutionsList += "<option value=" + institution.id + ">" + institution.composedName + "</option>";
    });
  }).done(function(){
    $selectInstitutions.html(institutionsList);
  }).fail(function(){
    console.log("error");
  }).always(function(){
    $selectInstitutions.trigger("liszt:updated");
  });
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
  
  $("form select[name$='partner']").chosen({
    no_results_text : "#noResultText",
    allow_single_deselect : true,
    search_contains : true
  });
  
  $("form .partnerTypes").chosen({
    allow_single_deselect : true,
    search_contains : true
  });
  
  $("form .countryList").chosen({
    allow_single_deselect : true,
    search_contains : true
  });
  
}
