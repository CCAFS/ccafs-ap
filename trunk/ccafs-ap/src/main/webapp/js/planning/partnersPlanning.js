$(document).ready(function() {
  //This function enables launch the pop up window
  popups();
  
  $("input:radio[name='activity\\.hasPartners']").on('change', togglePartnersDiv);
  if($("input:radio[name='activity\\.hasPartners']").val() != 'true'){
    $("#items").fadeOut();
  }

  $(".addActivityPartner").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newActivityPartner = $("#activityPartner-9999").clone(true);
    $(".addLink").before($newActivityPartner);
    renameActivityPartners();
    $newActivityPartner.fadeIn("slow");
    // Activate the chosen plugin
    $newActivityPartner.find("select[name$='partner']").chosen({no_results_text: $("#noResultText").val(),search_contains:true});
    $newActivityPartner.find(".partnerTypes").chosen({allow_single_deselect:true, search_contains:true});
    $newActivityPartner.find(".countryList").chosen({allow_single_deselect:true, search_contains:true});
  });

  $('.removeActivityPartner').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#activityPartner-" + removeId).hide("slow", function() {
      // removing division line.
      $(this).next("hr").remove();
      // removing div.
      $(this).remove();
      renameActivityPartners();
    });
  });
  
  $('.partnerTypes').change(updatePartnerList);
  $('.countryList').change(updatePartnerList);
  
  renameActivityPartners();
  
  $(".partnerTypes").each(function(){
    $(this).attr('data-placeholder', $("#partnerTypeDefault").val());
    $(this).trigger('change');
  });
  
  $(".countryList").each(function(){
    $(this).attr('data-placeholder', $("#countryListDefault").val());        
  });
  
  //Activate the chosen plugin to the existing partners  
  addChosen();
  
});

function togglePartnersDiv(event){
  var element = event.target;
  
  // If user indicates that the activity will have partners
  // show the fieldset to select them
  console.log($(element).val() == 'true');
  if($(element).val() == 'true'){
    $("#items").fadeIn();
  }else{
    // If the activity will not have partners then hide the div 
    // and delete all the existing partners
    $("#items").fadeOut();
    $(".activityPartner").remove();
  }
}

function renameActivityPartners() {
  $("#items .activityPartner").each(
      function(index, activityPartner) {
        // Changing attributes of each component in order to match
        // with the array order.
        // Main div.
        $(this).attr("id", "activityPartner-" + index);
        // Remove id
        $(this).find("[name$='id']").attr("name",
            "activity.activityPartners[" + index + "].id");
        // Remove link.
        $(this).find("[id^='removeActivityPartner-']").attr("id",
            "removeActivityPartner-" + index);
        // Partner type list
        $(this).find("[id$='partners_partnerTypeList']").attr("id",
            "partnerTypeList_" + index );
        // Country list
        $(this).find("[id$='partners_countryList']").attr("id",
            "countryList_" + index );
        // Partner List.
        $(this).find(".partnerName").find("[id$='partner']").attr(
            "id",
            "partners_activity_activityPartners_" + index
                + "__partner");
        $(this).find(".partnerName").find("[name$='partner']").attr(
            "name",
            "activity.activityPartners[" + index + "].partner");
        // Contact Name.
        $(this).find("[id$='contactName']").attr("id",
            "activity.activityPartners[" + index + "].contactName");
        $(this).find("[name$='contactName']").attr("name",
            "activity.activityPartners[" + index + "].contactName");
        $(this).find("[for$='contactName']").attr("for",
            "activity.activityPartners[" + index + "].contactName");
        // Contact Email.
        $(this).find("[id$='contactEmail']").attr("id",
            "activity.activityPartners[" + index + "].contactEmail");
        $(this).find("[name$='contactEmail']").attr("name",
            "activity.activityPartners[" + index + "].contactEmail");
        $(this).find("[for$='contactEmail']").attr("for",
            "activity.activityPartners[" + index + "].contactEmail");
      }
  );
}

// Activate the chosen plugin to the countries, partner types and 
// partners lists.
function addChosen() {
  $("select[name$='partner']").each(function() {
    // Check if its not the template partner field
    if ($(this).attr("name") != '__partner') {
      $(this).chosen({no_results_text: $("#noResultText").val(),search_contains:true});
    }
  });
  
  $(".partnerTypes").each(function() {
    // Check if its not the template partner types field
    if ($(this).attr("id") != 'partners_partnerTypeList') {
      $(this).chosen({allow_single_deselect:true,search_contains:true});
    }
  });
  
  $(".countryList").each(function() {
    // Check if its not the template countries field
    if ($(this).attr("id") != 'partners_countryList') {
      $(this).chosen({allow_single_deselect:true, search_contains:true});
    }
  });
}

function updatePartnerList(event, data){
  
  var partnersByFilterLink = '../json/partnersByFilter.do?';
  var indexID = $(event.target).attr('id').split('_')[1];
  
  // Return if the element is the template
  if(indexID == 'partnerTypeList'){
    return;
  }
  
  if($("#partnerTypeList_" + indexID).val() != -1){
    partnersByFilterLink += "partnerTypeID=" + $("#partnerTypeList_" + indexID).val();
  }
  
  if($("#countryList_" + indexID).val() != -1){
    partnersByFilterLink += "&countryID=" + $("#countryList_" + indexID).val();
  }
  
  $.getJSON(partnersByFilterLink, function(data) {
    var optionsHtml = "";
    if(data.partners == null){
      optionsHtml += "<option value='-1'>";
      optionsHtml += $("#noResultByFilterText").val();
      optionsHtml += "</option>";

      $("#partners_activity_activityPartners_" + indexID + "__partner").html(optionsHtml);
      $("#partners_activity_activityPartners_" + indexID + "__partner").trigger("liszt:updated");
    }else{
      var partners = data.partners;
      for ( var c = 0; c < partners.length; c++) {
        optionsHtml += "<option value='" + partners[c].id + "'>";
        optionsHtml += partners[c].name;
        optionsHtml += "</option>";
      }
      // Keep the selected value if is still in the list
      var selectedValue = $("#partners_activity_activityPartners_" + indexID + "__partner").val();
      $("#partners_activity_activityPartners_" + indexID + "__partner").html(optionsHtml);
      $("#partners_activity_activityPartners_" + indexID + "__partner").val(selectedValue);
      $("#partners_activity_activityPartners_" + indexID + "__partner").trigger("liszt:updated");
    }
  });
}