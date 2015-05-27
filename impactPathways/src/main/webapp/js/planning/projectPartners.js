$(document).ready(function(){
  attachEvents();
  // This function enables launch the pop up window
  popups();
  // Activate the chosen plugin to the existing partners
  addChosen();
  if (!$("div.projectPartner").length) {
    $("a.addProjectPartner").trigger("click");
  }
  
  function attachEvents(){
    // Partners Events
    $("a.addProjectPartner").click(addPartnerEvent);
    $(".removePartner").click(removePartnerEvent);
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
    var source = "../../json/institutionsByTypeAndCountry.do?institutionTypeID=" + partnerTypes + "&countryID=" + countryList;
    
    if (eventType == "filters-link")
      source = "../../json/institutionsByTypeAndCountry.do";
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
      search_contains : true
    });
    
    $("form select[name$='currentInstitution']").chosen({
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
  
  // -------------- Search users functions ------------------ //
  
  var dialog, form;
  var emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  var name = $("#name");
  var email = $("#email");
  var password = $("#password");
  var allFields = $([]).add(name).add(email).add(password);
  var tips = $(".validateTips");
  
  function updateTips(t){
    tips.text(t).addClass("ui-state-highlight");
    setTimeout(function(){
      tips.removeClass("ui-state-highlight", 1500);
    }, 500);
  }
  
  function checkLength(o,n,min,max){
    if (o.val().length > max || o.val().length < min) {
      o.addClass("ui-state-error");
      updateTips("Length of " + n + " must be between " + min + " and " + max + ".");
      return false;
    } else {
      return true;
    }
  }
  
  function checkRegexp(o,regexp,n){
    if (!(regexp.test(o.val()))) {
      o.addClass("ui-state-error");
      updateTips(n);
      return false;
    } else {
      return true;
    }
  }
  
  function addUser(){
    var valid = true;
    allFields.removeClass("ui-state-error");
    
    valid = valid && checkLength(name, "username", 3, 16);
    valid = valid && checkLength(email, "email", 6, 80);
    valid = valid && checkLength(password, "password", 5, 16);
    
    valid = valid && checkRegexp(name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter.");
    valid = valid && checkRegexp(email, emailRegex, "eg. ui@jquery.com");
    valid = valid && checkRegexp(password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9");
    
    if (valid) {
      $("#users tbody").append("<tr>" + "<td>" + name.val() + "</td>" + "<td>" + email.val() + "</td>" + "<td>" + password.val() + "</td>" + "</tr>");
      dialog.dialog("close");
    }
    return valid;
  }
  
  dialog = $("#dialog-form").dialog({
    autoOpen : false,
    height : 300,
    width : 350,
    modal : true,
    show : {
      effect : "size",
      duration : 500
    },
    buttons : {
      "Search an user" : addUser,
      Cancel : function(){
        $(this).dialog("close");
      }
    },
    close : function(){
      form[0].reset();
      allFields.removeClass("ui-state-error");
    }
  });
  
  form = dialog.find("form").on("submit", function(event){
    event.preventDefault();
    addUser();
  });
  
  $(".searchUser").on("click", function(){
    event.preventDefault();
    dialog.dialog("open");
  });
  
});
