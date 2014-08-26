//Limits for textarea input
var lWordsElemetDesc = 300;
$(document).ready(init);

function init(){
  // Started listeners
  attachEvents();
  // This function enables launch the pop up window
  popups();
  // Activate the chosen plugin to the existing partners
  addChosen();
  if (!$("div.activityPartner").exists()) {
    $("#addActivityPartner .addButton").trigger("click");
  }
  applyWordCounter($("textarea"), lWordsElemetDesc);
}

function attachEvents(){
  // Add partner block
  $("#addActivityPartner .addButton").click(addPartnerEvent);
  // Remove partner block
  $(".removeActivityPartner").click(removePartnerEvent);
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
  var source = "../../../json/institutionsByTypeAndCountry.do?institutionTypeID=" + partnerTypes + "&countryID=" + countryList;
  if (eventType == "filters-link")
    source = "../../../json/institutionsByTypeAndCountry.do";
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
  var $parent = $(e.target).parent();
  $parent.hide("slow", function(){
    $parent.remove();
    setActivityPartnersIndexes();
  });
}

function addPartnerEvent(e){
  e.preventDefault();
  var $newElement = $("#activityPartnerTemplate").clone(true).removeAttr("id").addClass("activityPartner");
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
  setActivityPartnersIndexes();
}

function setActivityPartnersIndexes(){
  $("div.activityPartner").each(function(index,element){
    console.log("partner" + index);
    var elementName = "activity.activityPartners[" + index + "].";
    $(element).attr("id", "activityPartner-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[id$='partnerIndex']").html(index + 1);
    $(element).find("[name$='].id']").attr("name", elementName + "id");
    $(element).find("[name$='partner']").attr("name", elementName + "partner");
    $(element).find("[name$='contactName']").attr("name", elementName + "contactName");
    $(element).find("[name$='contactEmail']").attr("name", elementName + "contactEmail");
    $(element).find("[name$='contribution']").attr("name", elementName + "contribution");
  });
}

function addChosen(){
  $("form select[name$='partner']").chosen({
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
