var $removePartnerDialog, $projectPPAPartners;
var allPPAInstitutions;
var lWordsResp = 100;

$(document).ready(init);

function init() {
  if (editable){
    // Setting global variables
    $removePartnerDialog = $('#partnerRemove-dialog');
    $partnersBlock = $('#projectPartnersBlock');
    allPPAInstitutions = JSON.parse($('#allPPAInstitutions').val());
    $projectPPAPartners = $('#projectPPAPartners');
    // Update initial project CCAFS partners list for each partner
    updateProjectPPAPartnersLists();
    // Attaching listeners
    attachEvents();
    // This function enables launch the pop up window
    popups();
    // Activate the chosen to the existing partners
    addChosen();
    // Applying word counters to form fields
    applyWordCounter($("textarea.resp"), lWordsResp);
    applyWordCounter($("#lessons textarea"), lWordsResp);
    
    initItemListEvents();
    
    validateEvent([ "#justification" ]);  
  }
  $('.loadingBlock').hide().next().fadeIn(500);
}

function attachEvents() {
  // Partners Events
  $("a.addProjectPartner").on('click',addPartnerEvent);
  $(".removePartner").on('click',removePartnerEvent);
  
  // Contacts Events
  $(".addContact a.addLink").on('click',addContactEvent);
  $(".removePerson").on('click',removePersonEvent);
  
  // Partners filters
  $(".filters-link").click(function(event) {
    var $filterContent = $(event.target).next();
    if($filterContent.is(":visible")) {
      updateOrganizationsList(event);
    }
    $filterContent.slideToggle();
  });
  
  // When Partner Type change
  $("select.partnerTypes, select.countryList").change(updateOrganizationsList);
  
  // When organization change
  $("select.institutionsList").on("change",function(e){
    updateProjectPPAPartnersLists();
  });
  
  // When partnerPersonType change
  $("select.partnerPersonType").on("change",function(e){
    var contact = new PartnerPersonObject($(e.target).parents('.contactPerson'));
    contact.changeType();
  });
}

function updateOrganizationsList(e) {
  var $parent = $(e.target).parents('.projectPartner');
  var partner = new PartnerObject($parent);
  var $selectInstitutions = $parent.find("select[name$='institution']"); // Institutions list
  var optionSelected = $selectInstitutions.find('option:selected').val(); // Institution selected
  var source = baseURL + "/json/institutionsByTypeAndCountry.do";
  if($(e.target).attr("class") != "filters-link") {
    var partnerTypes = $parent.find("select.partnerTypes").find('option:selected').val(); // Type value
    var countryList = $parent.find("select.countryList").find('option:selected').val(); // Value value
    source += "?institutionTypeID=" + partnerTypes + "&countryID=" + countryList;
  }
  $.ajax({
      url: source,
      beforeSend: function() {
        partner.startLoader();
        $selectInstitutions.empty().append(setOption(-1, "Select an option"));
      },
      success: function(data) {
        $.each(data.institutions, function(index,institution) {
          $selectInstitutions.append(setOption(institution.id, institution.composedName));
        });
      },
      complete: function() {
        partner.stopLoader();
        $selectInstitutions.val(optionSelected);
        $selectInstitutions.trigger("liszt:updated");
      }
  });
}

function updateProjectPPAPartnersLists(){
  // Collecting list CCAFS partners from all project partners
  $projectPPAPartners.empty();
  $partnersBlock.find('.projectPartner').each(function(i,projectPartner){
    var partner = new PartnerObject($(projectPartner));
    if(allPPAInstitutions.indexOf(partner.institutionId) != -1 ){
      partner.hidePPAs();
      $projectPPAPartners.append(setOption(partner.institutionId, partner.institutionName));
    }else{
      if (partner.institutionId == -1){
        partner.hidePPAs();
      }else{
        partner.showPPAs();
      }
    }
  });
  // Filling CCAFS partners lists for each project partner
  $partnersBlock.find('.projectPartner').each(function(i,partner){
    var $select = $(partner).find('select.ppaPartnersSelect');
    $select.empty().append(setOption(-1, "Select an option"));
    $select.append($projectPPAPartners.html());
    // Removing of the list CCAFS partners previously selected by project partner
    $(partner).find('li input.id').each(function(i_id,id) {
      $select.find('option[value=' + $(id).val() + ']').remove();
    });
    $select.trigger("liszt:updated");
  });
  
}



// Partner Events
function removePartnerEvent(e) {
  var partner = new PartnerObject($(e.target).parent().parent());
  if(partner.id == "-1"){
    partner.remove();
    return
  }
  e.preventDefault();
  partner.remove();
/*
 * $.ajax({ method: "GET", url: baseURL + "/json/projectPartnersDelete.do", data: { projectPartnerID: partner.id },
 * beforeSend: function() { partner.startLoader(); $removePartnerDialog.find('.activities').hide();
 * $removePartnerDialog.find('.deliverables').hide(); $removePartnerDialog.find('.projectPartners').hide();
 * $removePartnerDialog.find('.activities ul').empty(); $removePartnerDialog.find('.deliverables ul').empty();
 * $removePartnerDialog.find('.projectPartners ul').empty(); }, success: function(data) { partner.stopLoader();
 * if(data.message) { var dialogOptions = { width: 500, modal: true, buttons: { Cancel: function() {
 * $(this).dialog("close"); } } }; $removePartnerDialog.find('p.message').text(data.message);
 * if(data.linkedActivities.length > 0) { $removePartnerDialog.find('.activities').show(); $.each(data.linkedActivities,
 * function(i,activity) { $removePartnerDialog.find('.activities ul').append("<li>" + activity.title + "</li>"); }); }
 * else { dialogOptions.buttons = { Cancel: function() { $(this).dialog("close"); }, "Remove partner": function() {
 * $(this).dialog("close"); partner.remove(); } }; } if(data.linkedDeliverables.length > 0) {
 * $removePartnerDialog.find('.deliverables').show(); $.each(data.linkedDeliverables, function(i,deliverable) {
 * $removePartnerDialog.find('.deliverables ul').append("<li>" + deliverable.title + "</li>"); }); }
 * if(data.linkedProjectPartners.length > 0) { $removePartnerDialog.find('.projectPartners').show();
 * $.each(data.linkedProjectPartners, function(i,projectPartner) { $removePartnerDialog.find('.projectPartners
 * ul').append("<li>" + projectPartner.title + "</li>"); }); } $removePartnerDialog.dialog(dialogOptions); } else {
 * partner.remove(); } } });
 */
}

function addPartnerEvent(e) {
  e.preventDefault();
  var $newElement = $("#projectPartner-template").clone(true).removeAttr("id");
  $(e.target).parent().before($newElement);
  $newElement.show("slow");
  // Activate the chosen plugin for new partners created
  $newElement.find("select").chosen({
      no_results_text: $("#noResultText").val(),
      search_contains: true
  });
  setProjectPartnersIndexes();
}

function addContactEvent(e) {
  e.preventDefault();
  var $newElement = $("#contactPerson-template").clone(true).removeAttr("id");
  $(e.target).parent().before($newElement);
  $newElement.show("slow");
  // Activate the chosen plugin for new partners created
  $newElement.find("select").chosen({
      no_results_text: $("#noResultText").val(),
      search_contains: true
  });
  setProjectPartnersIndexes();
}

function removePersonEvent(e){
  e.preventDefault();
  var person = new PartnerPersonObject($(e.target).parent());
  person.remove();
}

function setProjectPartnersIndexes() {
  $partnersBlock.find(".projectPartner").each(function(index,element) {
    var partner = new PartnerObject($(element));
    partner.setIndex($('#partners-name').val(), index);
  });
}

/**
 * Items list functions
 */

function initItemListEvents() {
  $('.ppaPartnersList select').on('change', function(e) {
    addItemList($(this).find('option:selected'));
  });
  $('ul li .remove').on('click', function(e) {
    removeItemList($(this).parents('li'));
  });
}

function removeItemList($item) {
  // Adding option to the select
  var $select = $item.parents('.panel').find('select');
  $select.append(setOption($item.find('.id').val(), $item.find('.name').text()));
  $select.trigger("liszt:updated");
  // Removing from list
  $item.hide("slow", function() {
    $item.remove();
    setProjectPartnersIndexes();
  });
}

function addItemList($option) {
  var $select = $option.parent();
  var $list = $option.parents('.panel').find('ul.list');
  // Adding element to the list
  var $li = $("#ppaListTemplate").clone(true).removeAttr("id");
  $li.find('.id').val($option.val());
  $li.find('.name').html($option.text());
  $li.appendTo($list).hide().show('slow');
  // Removing option from select
  $option.remove();
  $select.trigger("liszt:updated");
  setProjectPartnersIndexes();
}

// Activate the chosen plugin to the countries, partner types and partners lists.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}

/**
 * PartnerObject
 * 
 * @param {JqueryObject} Project partner
 */

function PartnerObject(partner) {
  var types = [];
  this.id = parseInt($(partner).find('.partnerId').val());
  this.institutionId = parseInt($(partner).find('.institutionsList').val());
  this.institutionName = $(partner).find('.institutionsList option[value='+this.institutionId+']').text();
  this.ppaPartnersList = $(partner).find('.ppaPartnersList');
  this.setIndex = function(name, index) {
    var elementName = name+"["+index+"].";
    // Update index for project Partner
    $(partner).find(".leftHead .index").html(index + 1);
    $(partner).find("[id$='id']").attr("name", elementName + "id");
    $(partner).find("[id$='institution']").attr("name", elementName + "institution");
    // Update index for CCAFS Partners
    $(partner).find('.ppaPartnersList ul.list li').each(function(li_index,li) {
      $(li).find('.id').attr("name", elementName + "contributeInstitutions" + "[" + li_index + "].id");
    });
    // Update index for partner persons
    $(partner).find('.contactPerson').each(function(i, partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      contact.setIndex(elementName, i);
    });
  };
  this.checkLeader = function(){
    if ($(partner).find('.contactPerson.PL').length == 0){
      $(partner).removeClass('leader');
    }else{
      $(partner).addClass('leader');
      types.push('Leader');
    }
  };
  this.checkCoordinator = function(){
    if ($(partner).find('.contactPerson.PC').length == 0){
      $(partner).removeClass('coordinator');
    }else{
      $(partner).addClass('coordinator');
      types.push('Coordinator');
    }
  };
  this.changeType = function(){
    types = [];
    this.checkLeader();
    this.checkCoordinator();
    if (types.length != 0){
      $(partner).find('strong.type').text(' ('+types.join(", ")+')');
    }else{
      $(partner).find('strong.type').text('');
    }
  };
  this.remove = function() {
    $(partner).hide("slow", function() {
      $(partner).remove();
      setProjectPartnersIndexes();
    });
  };
  this.showPPAs = function() {
   $(this.ppaPartnersList).slideDown();
  };
  this.hidePPAs = function() {
    $(this.ppaPartnersList).slideUp();
  };
  this.startLoader = function() {
    $(partner).find('.loading').fadeIn();
  };
  this.stopLoader = function() {
    $(partner).find('.loading').fadeOut();
  };
}

/**
 * PartnerPersonObject
 * 
 * @param {JqueryObject} Partner person
 */
function PartnerPersonObject(partnerPerson) {
  this.id = parseInt($(partnerPerson).find('.partnerPersonId').val());
  this.type = $(partnerPerson).find('select.partnerPersonType').val();
  this.setPartnerType = function(type){
    $(partnerPerson).find('select.partnerPersonType').val(type).trigger("liszt:updated");;
  };
  this.changeType = function(){
    var partner = new PartnerObject($(partnerPerson).parents('.projectPartner'));
    if (this.type == 'PL'){
      setUniquePartnerType(this.type);
    }
    $(partnerPerson).removeClass('PC PL CP -1').addClass(this.type);
    this.setPartnerType(this.type);
    partner.changeType();
  };
  this.setIndex = function(name, index) {
    var elementName = name+"partnerPersons["+index+"].";
    $(partnerPerson).find(".leftHead .index").html(index + 1);
    $(partnerPerson).find(".partnerPersonId").attr("name", elementName + "id");
    $(partnerPerson).find(".partnerPersonType").attr("name", elementName + "type");
    $(partnerPerson).find(".userId").attr("name", elementName + "user");
    $(partnerPerson).find(".resp").attr("name", elementName + "responsibilities");
  };
  this.remove = function() {
    var partner = new PartnerObject($(partnerPerson).parents('.projectPartner'));
    $(partnerPerson).hide("slow", function() {
      $(partnerPerson).remove();
      partner.changeType(this.type);
      setProjectPartnersIndexes();
    });
  };
}

function setUniquePartnerType(type){
  $partnersBlock.find('.projectPartner').each(function(i,partner){
    var projectPartner = new PartnerObject($(partner));
    $(partner).find('.contactPerson').each(function(i, partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      if (contact.type == type){
        $(partnerPerson).removeClass('PC PL CP -1').addClass('CP');
        contact.setPartnerType('CP');
      }
    });
    projectPartner.changeType();
  });
}
