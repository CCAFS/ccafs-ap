var $removePartnerDialog, $projectPPAPartners;
var canUpdatePPAPartners, allPPAInstitutions, partnerPersonTypes, leaderType, coordinatorType, defaultType;
var lWordsResp = 100;

$(document).ready(init);

function init() {
  // Setting global variables
  $removePartnerDialog = $('#partnerRemove-dialog');
  $partnersBlock = $('#projectPartnersBlock');
  allPPAInstitutions = JSON.parse($('#allPPAInstitutions').val());
  $projectPPAPartners = $('#projectPPAPartners');
  canUpdatePPAPartners = ($("#canUpdatePPAPartners").val() === "true");
  leaderType = 'PL';
  coordinatorType = 'PC';
  defaultType = 'CP';
  partnerPersonTypes = [
      coordinatorType, leaderType, defaultType, '-1'
  ];
  if(editable) {
    // Draggable project partners
    $partnersBlock.sortable({
        placeholder: "ui-state-highlight",
        handle: ".leftHead",
        cursor: "move",
        opacity: 0.9,
        containment: "parent",
        revert: true,
        update: function() {
          setProjectPartnersIndexes();
        }
    });
    $('.projectPartner > .leftHead').css({
      cursor: 'move'
    });
    $partnersBlock.disableSelection();
    // Remove PPA institutions from partner institution list when there is not privileges to update PPA Partners
    if(!canUpdatePPAPartners) {
      removePPAPartnersFromList('#projectPartner-template .institutionsList');
    }
    // Update initial project CCAFS partners list for each partner
    updateProjectPPAPartnersLists();
    // Attaching listeners
    // This function enables launch the pop up window
    popups();
    // Activate the chosen to the existing partners
    addChosen();
    // Applying word counters to form fields
    applyWordCounter($("textarea.resp"), lWordsResp);
    applyWordCounter($("#lessons textarea"), lWordsResp);

    initItemListEvents();

    validateEvent([
      "#justification"
    ]);
  }
  attachEvents();
  if(($partnersBlock.find('.projectPartner').length == 0) && editable) {
    $("a.addProjectPartner").trigger('click');
  }
  $('.loadingBlock').hide().next().fadeIn(500);
}

function attachEvents() {
  /** Project partner Events ** */
  // Add a project partner Event
  $("a.addProjectPartner").on('click', addPartnerEvent);
  // Remove a project partner Event
  $(".removePartner").on('click', removePartnerEvent);
  // When Partner Type change
  $("select.partnerTypes, select.countryList").change(updateOrganizationsList);
  // When organization change
  $("select.institutionsList").on("change", function(e) {
    updateProjectPPAPartnersLists(e);
  });
  // Partners filters
  $(".filters-link").click(function(event) {
    var $filterContent = $(event.target).next();
    if($filterContent.is(":visible")) {
      updateOrganizationsList(event);
    }
    $filterContent.slideToggle();
  });

  /** Partner Person Events ** */
  // Add partner Person Event
  $(".addContact a.addLink").on('click', addContactEvent);
  // Remove partner person event
  $(".removePerson").on('click', removePersonEvent);
  // When partnerPersonType change
  $("select.partnerPersonType").on("change", function(e) {
    var $contactPerson = $(e.target).parents('.contactPerson');
    var contact = new PartnerPersonObject($contactPerson);
    var partner = new PartnerObject($contactPerson.parents('.projectPartner'));
    if(contact.type == leaderType) {
      if(projectLeaders() > 1) {
        var messages = '<li>A project leader has been selected before, it will become in a contact person</li>';
        // Show a pop up with the message
        $("#contactChangeType-dialog").find('.messages').append(messages);
        $("#contactChangeType-dialog").dialog({
            modal: true,
            width: 400,
            buttons: {
              Close: function() {
                $(this).dialog("close");
              }
            },
            close: function() {
              $(this).find('.messages').empty();
            }
        });
      }
      setPartnerTypeToDefault(contact.type);
    } else if(contact.type == coordinatorType) {
      setPartnerTypeToDefault(contact.type);
    }
    contact.changeType();
    partner.changeType();
  });
  // Event to open dialog box and search an contact person
  $(".searchUser, input.userName").on(
      "click",
      function(e) {
        var person = new PartnerPersonObject($(e.target).parents('.contactPerson'));
        // Validate if the person has any activity related for be changed
        if(!person.canEditEmail) {
          var messages = '';
          e.stopImmediatePropagation();
          messages +=
              '<li>This contact cannot be changed due to is currently the Activity Leader for '
                  + person.getRelationsNumber('activities') + ' activity(ies)';
          messages += '<ul>';
          messages += person.getRelations('activities');
          messages += '</ul>';
          messages += '</li>';
          // Show a pop up with the message
          $("#contactChange-dialog").find('.messages').append(messages);
          $("#contactChange-dialog").dialog({
              modal: true,
              width: 400,
              buttons: {
                Close: function() {
                  $(this).dialog("close");
                }
              },
              close: function() {
                $(this).find('.messages').empty();
              }
          });
        }
      });
  // Event when click in a relation tag of partner person
  $(".tag").on("click", function(e) {
    var $relations = $(this).next().html();
    $('#relations-dialog').dialog({
        modal: true,
        width: 400,
        buttons: {
          Close: function() {
            $(this).dialog("close");
          }
        },
        open: function() {
          $(this).find('ul').html($relations);
        },
        close: function() {
          $(this).find('ul').empty();
        }
    });
  });
}

function projectLeaders() {
  var leaders = 0;
  $partnersBlock.find('.contactPerson').each(function(i,partnerPerson) {
    var contact = new PartnerPersonObject($(partnerPerson));
    if(contact.isLeader()) {
      leaders++;
    }
  });
  return leaders;
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
        if(!canUpdatePPAPartners) {
          removePPAPartnersFromList($selectInstitutions);
        }
      },
      complete: function() {
        partner.stopLoader();
        $selectInstitutions.val(optionSelected);
        $selectInstitutions.trigger("liszt:updated");
      }
  });
}

function removePPAPartnersFromList(list) {
  for(var i = 0, len = allPPAInstitutions.length; i < len; i++) {
    $(list).find('option[value=' + allPPAInstitutions[i] + ']').remove();
  }
  $(list).trigger("liszt:updated");
}

function updateProjectPPAPartnersLists(e) {
  $projectPPAPartners.empty();
  var projectInstitutions = [];
  // Loop for all projects partners
  $partnersBlock.find('.projectPartner').each(function(i,projectPartner) {
    var partner = new PartnerObject($(projectPartner));
    // Collecting partners institutions
    projectInstitutions.push(parseInt(partner.institutionId));
    // Validating if the partners is CCAFS Partner
    if(partner.isPPA()) {
      partner.hidePPAs();
      // Collecting list CCAFS partners from all project partners
      $projectPPAPartners.append(setOption(partner.institutionId, partner.institutionName));
    } else {
      if(partner.institutionId == -1) {
        partner.hidePPAs();
      } else {
        partner.showPPAs();
      }
    }
  });
  // Validating if the institution chosen is already selected
  if(e) {
    var $fieldError = $(e.target).parents('.partnerName').find('p.fieldError');
    $fieldError.text('');
    var count = 0;
    // Verify if the partner is already selected
    for(var i = 0; i < projectInstitutions.length; ++i) {
      if(projectInstitutions[i] == e.target.value) {
        count++;
      }
    }
    // If there is one selected , show an error message
    if(count > 1) {
      $fieldError.text('This institution is already selected').addClass('animated flipInX');
    }
  }
  // Filling CCAFS partners lists for each project partner
  $partnersBlock.find('.projectPartner').each(function(i,partner) {
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

function setPartnerTypeToDefault(type) {
  $partnersBlock.find('.projectPartner').each(function(i,partner) {
    var projectPartner = new PartnerObject($(partner));
    $(partner).find('.contactPerson').each(function(i,partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      if(contact.type == type) {
        $(partnerPerson).removeClass(partnerPersonTypes.join(' ')).addClass(defaultType);
        contact.setPartnerType(defaultType);
      }
    });
    projectPartner.changeType();
  });
}

function removePartnerEvent(e) {
  e.preventDefault();
  var partner = new PartnerObject($(e.target).parent().parent());
  var messages = "";
  var activities = partner.getRelationsNumber('activities');
  var deliverables = partner.getRelationsNumber('deliverables');
  var partnerContributions = partner.hasPartnerContributions();
  var removeDialogOptions = {
      modal: true,
      width: 500,
      buttons: {},
      close: function() {
        $("#partnerRemove-dialog").find('.messages').empty();
      }
  };
  // Validate if there are any deliverable linked to any contact person of this partner
  if(deliverables > 0) {
    messages +=
        '<li>Please bear in mind that if you delete this contact, ' + deliverables
            + ' deliverables relations will be deleted</li>';
    removeDialogOptions.buttons = {
        "Remove partner": function() {
          partner.remove();
          $(this).dialog("close");
        },
        Close: function() {
          $(this).dialog("close");
        }
    };
  }
  // Validate if the CCAFS partner has any contribution to another partner
  if(partner.isPPA() && (partnerContributions.length > 0)) {
    messages += '<li>This partner cannot be deleted due to is currently contributing to another partner ';
    messages += '<ul>';
    for(var i = 0, len = partnerContributions.length; i < len; i++) {
      messages += '<li>' + partnerContributions[i] + '</li>';
    }
    messages += '</ul> </li>';
    removeDialogOptions.buttons = {
      Close: function() {
        $(this).dialog("close");
      }
    };
  }
  // Validate if the project partner has any person as leader role
  if(partner.hasLeader()) {
    messages += '<li>Please indicate another project leader before deleting this partner.</li>';
    removeDialogOptions.buttons = {
      Close: function() {
        $(this).dialog("close");
      }
    };
  }
  // Validate if the user has privileges to remove CCAFS Partners
  if(partner.isPPA() && !canUpdatePPAPartners) {
    messages +=
        '<li>You don\'t have privileges to delete CCAFS Partners, please contact the ML to delete this partner. </li>';
    removeDialogOptions.buttons = {
      Close: function() {
        $(this).dialog("close");
      }
    };
  }
  // Validate if there are any activity linked to any contact person of this partner
  if(activities > 0) {
    messages +=
        '<li>This partner cannot be deleted due to is currently related with ' + activities + ' activities.</li>';
    removeDialogOptions.buttons = {
      Close: function() {
        $(this).dialog("close");
      }
    };
  }

  if(messages === "") {
    // Remove partner if there is not any problem
    partner.remove();
  } else {
    // Show pop up if there are any message
    $("#partnerRemove-dialog").find('.messages').append(messages);
    $("#partnerRemove-dialog").dialog(removeDialogOptions);
  }

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

function removePersonEvent(e) {
  e.preventDefault();
  var person = new PartnerPersonObject($(e.target).parent());
  var messages = "";
  var activities = person.getRelationsNumber('activities');
  var deliverables = person.getRelationsNumber('deliverables');
  // Validate if the person type is PL
  if(person.isLeader()) {
    messages += '<li>Please indicate another project leader before deleting this contact.</li>';
  }
  // Validate if there are any activity linked to this person
  if(activities > 0) {
    messages +=
        '<li>This contact cannot be deleted due to is currently the Activity Leader for ' + activities
            + ' activity(ies)';
    messages += '<ul>';
    messages += person.getRelations('activities');
    messages += '</ul>';
    messages += '</li>';
  }
  // Validate if there are any deliverable linked to this person
  if(deliverables > 0) {
    messages +=
        '<li>Please bear in mind that if you delete this contact, ' + deliverables
            + ' deliverables relations will be deleted</li>';
  }
  if(messages === "") {
    // Remove person if there is not any message
    person.remove();
  } else {
    // Show a pop up with the message
    $("#contactRemove-dialog").find('.messages').append(messages);
    $("#contactRemove-dialog").dialog({
        modal: true,
        width: 400,
        buttons: {
          Close: function() {
            $(this).dialog("close");
          }
        },
        close: function() {
          $(this).find('.messages').empty();
        }
    });
  }
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
 * @param {DOM} Project partner
 */

function PartnerObject(partner) {
  var types = [];
  this.id = parseInt($(partner).find('.partnerId').val());
  this.institutionId = parseInt($(partner).find('.institutionsList').val());
  this.institutionName =
      $('#projectPartner-template .institutionsList option[value=' + this.institutionId + ']').text();
  this.ppaPartnersList = $(partner).find('.ppaPartnersList');
  this.setIndex = function(name,index) {
    var elementName = name + "[" + index + "].";
    // Update index for project Partner
    $(partner).find("> .leftHead .index").html(index + 1);
    $(partner).find("[id$='id']").attr("name", elementName + "id");
    $(partner).find(".institutionsList").attr("name", elementName + "institution");
    // Update index for CCAFS Partners
    $(partner).find('.ppaPartnersList ul.list li').each(function(li_index,li) {
      $(li).find('.id').attr("name", elementName + "partnerContributors" + "[" + li_index + "].institution.id");
    });
    // Update index for partner persons
    $(partner).find('.contactPerson').each(function(i,partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      contact.setIndex(elementName, i);
    });
  };
  this.hasPartnerContributions = function() {
    var partners = [];
    var institutionId = this.institutionId;
    $partnersBlock.find(".projectPartner").each(function(index,element) {
      var projectPartner = new PartnerObject($(element));
      $(element).find('.ppaPartnersList ul.list li input.id').each(function(i_id,id) {
        if($(id).val() == institutionId) {
          partners.push(projectPartner.institutionName);
        }
      });
    });
    return partners;
  };
  this.hasLeader = function() {
    var result = false;
    $(partner).find('.contactPerson').each(function(i,partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      if(contact.isLeader()) {
        result = true;
      }
    });
    return result;
  };
  this.isPPA = function() {
    if(allPPAInstitutions.indexOf(parseInt($(partner).find('.institutionsList').val())) != -1) {
      return true;
    } else {
      return false;
    }
  };
  this.getRelationsNumber = function(relation) {
    var count = 0;
    $(partner).find('.contactPerson').each(function(i,partnerPerson) {
      var contact = new PartnerPersonObject($(partnerPerson));
      count += contact.getRelationsNumber(relation);
    });
    return count;
  };
  this.checkLeader = function() {
    if($(partner).find('.contactPerson.PL').length == 0) {
      $(partner).removeClass('leader');
    } else {
      $(partner).addClass('leader');
      types.push('Leader');
    }
  };
  this.checkCoordinator = function() {
    if($(partner).find('.contactPerson.PC').length == 0) {
      $(partner).removeClass('coordinator');
    } else {
      $(partner).addClass('coordinator');
      types.push('Coordinator');
    }
  };
  this.changeType = function() {
    types = [];
    this.checkLeader();
    this.checkCoordinator();
    if(types.length != 0) {
      $(partner).find('strong.type').text(' (' + types.join(", ") + ')');
    } else {
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
 * @param {DOM} Partner person
 */
function PartnerPersonObject(partnerPerson) {
  this.id = parseInt($(partnerPerson).find('.partnerPersonId').val());
  this.type = $(partnerPerson).find('.partnerPersonType').val();
  this.canEditEmail = ($(partnerPerson).find('input.canEditEmail').val() === "true");
  this.setPartnerType = function(type) {
    $(partnerPerson).find('.partnerPersonType').val(type).trigger("liszt:updated");
  };
  this.changeType = function() {
    $(partnerPerson).removeClass(partnerPersonTypes.join(' ')).addClass(this.type);
    this.setPartnerType(this.type);
  };
  this.getRelationsNumber = function(relation) {
    return parseInt($(partnerPerson).find('.tag.' + relation + ' span').text()) || 0;
  };
  this.getRelations = function(relation) {
    return $(partnerPerson).find('.tag.' + relation).next().html();
  };
  this.setIndex = function(name,index) {
    var elementName = name + "partnerPersons[" + index + "].";
    // $(partnerPerson).find(".leftHead .index").html(index + 1);
    $(partnerPerson).find(".partnerPersonId").attr("name", elementName + "id");
    $(partnerPerson).find(".partnerPersonType").attr("name", elementName + "type");
    $(partnerPerson).find(".userId").attr("name", elementName + "user.id");
    $(partnerPerson).find(".resp").attr("name", elementName + "responsibilities");
  };
  this.isLeader = function() {
    return(this.type == leaderType);
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
