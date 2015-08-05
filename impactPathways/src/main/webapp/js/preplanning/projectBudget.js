// Global VARS
var $allBudgetInputs, $overheadInputs, $CCAFSBudgetInputs, $selectAddProject;
var projectBudget,projectBudgetByYear,bilateralBudget,bilateralBudgetByYear;
var projectType;
var editable = true;

$(document).ready(init);

function init() {
  // Setting Global vars
  $allBudgetInputs = $("input.projectBudget");
  $genderBudgetInputs = $('input.projectGenderBudget');
  $overheadInputs = $("input[name$=isfullyInstitutionalCost]");
  $selectAddProject = $("select.addProject");
  
  projectType = "."+$('#projectType').val();
  
  projectBudget = new BudgetObject('#totalProjectBudget', projectType, false);
  projectBudgetByYear = new BudgetObject('#totalProjectBudgetByYear', projectType , true);
  bilateralBudget = new BudgetObject('#totalBilateralBudget', '.W3_BILATERAL', false);
  bilateralBudgetByYear = new BudgetObject('#totalBilateralBudgetByYear', '.W3_BILATERAL', true);
  
  // This function enables launch the pop up window
  popups();

  // Attach events
  attachEvents();
  
  // Loading projects to be added
  loadInitialCoreProjects();

  // Show table when page is loaded
  $("#budgetTables").fadeIn("slow");

  // Active initial currency format to all inputs
  $allBudgetInputs.attr("autocomplete", "off").trigger("focusout").trigger("keyup");
  $genderBudgetInputs.attr("autocomplete", "off").trigger("focusout").trigger("keyup");

  // Validate justification and information
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  // Regenerating hash from form information
  setFormHash();
}

function attachEvents() {
  // Events for amount inputs
  $allBudgetInputs.on("keydown", isNumber).on("focusout", setCurrency).on("focus", removeCurrency).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
        projectBudget.calculateBudget();
        projectBudgetByYear.calculateBudget();
        bilateralBudget.calculateBudget();
        bilateralBudgetByYear.calculateBudget();
        calculateGenderBudget($(e.target).parents('.partnerBudget'));
  });
  
  // Events for percentage inputs
  $genderBudgetInputs.on("keydown", isNumber).on("focusout", setPercentage).on("focus", removePercentage).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
        isPercentage(e);
        calculateGenderBudget($(e.target).parents('.partnerBudget'));
  });

  // Overhead (for bilateral projects) radio buttons event
  $overheadInputs.on("change", function(e) {
    var $content = $(e.target).parents('#overhead').find('.overhead-block');
    if($(e.target).val() === "0") {
      $content.slideDown('slow');
    } else {
      $content.slideUp('slow');
    }
  });
  
  // Enable save with tabs when is saveable and exist an target
  if($("#targetYear").exists()) {
    $("li.yearTab").on("click", function(e) {
      var $yearTab = $(this);
      if(isChanged()) {
        e.preventDefault();
        $("#dialog-confirm").dialog({
          buttons: {
              "Save": function() {
                var yearTarget = $yearTab.attr("id").split("-")[1];
                var $tempField = $(this).find('.justification');
                $tempField.removeClass('fieldError');
                if($tempField.val().length > 0){
                  $('#justification').val($tempField.val());
                  $('#year').val(yearTarget);
                  $("#budget_save").trigger("click");                  
                  $(this).dialog("close");
                }else{
                  $tempField.addClass('fieldError');
                }
              },
              "Discard changes": function() { 
                window.location.href = $yearTab.find('a').attr('href');
              }
          }
        });
      } else {
        return
      }
    });
  }
  
  // Get out format for amount and percentage inputs on submit
  $("form").submit(function(event) {
    $allBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    $genderBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    return;
  });
}

// Function to load all core projects with ajax
function loadInitialCoreProjects() {
  $selectAddProject.chosen({
    search_contains: true
  });
  $.ajax({
      'url': '../../coreProjects.do',
      beforeSend: function() {
        $selectAddProject.empty().append(setOption(-1, "Please select a project"));
      },
      success: function(data) {
        // Getting core projects previously selected
        var coreProjectsIds = [];
        $('#linkedProjects .budget input.budgetId').each(function(i_id,id) {
          coreProjectsIds.push($(id).val().toString());
        });
        // Setting core projects allowed to select
        $.each(data.projects, function(i,project) {
          if($.inArray(project.id.toString(), coreProjectsIds) == -1) {
            $selectAddProject.append(setOption(project.id, project.id + " - " + project.title));
          }
        });
      },
      complete: function() {
        $selectAddProject.trigger("liszt:updated");
      }
  });
} 

function BudgetObject(budget, type, byYear) {    
  this.obj = $(budget);
  this.span =$(this.obj).find('span');  
  this.input = $(this.obj).find('input');
  this.yearValue= parseFloat(totalBudget($('input'+type)));
  this.getValue = $(this.input).val();
  this.setValue = function (value){
    $(this.span).html(setCurrencyFormat(value));
    $(this.input).val(value);
  };
  this.calculateBudget = function(){ 
    var result = parseFloat(totalBudget($('input'+type)));
    if (!byYear){
      result += (this.getValue - this.yearValue);
    }
    this.setValue(result); 
  };
}

function calculateGenderBudget(partnerBudget){
  var percentage = removePercentageFormat($(partnerBudget).find('input.projectGenderBudget').val())||0;
  var value = removeCurrencyFormat($(partnerBudget).find('input.projectBudget').val())||0;
  var result = (value/100)*percentage;
  $(partnerBudget).find('input.projectGenderBudget').parents('.budget').find('.inputTitle span').text(setCurrencyFormat(result));
}

function totalBudget($inputList) {
  var Amount = 0;
  $inputList.each(function(index,amount) {
    if(!$(amount).val().length == 0) {
      Amount += removeCurrencyFormat($(amount).val());
    }
  });
  return Amount;
}

function setCurrency(event) {
  var $input = $(event.target);
  if($input.val().length == 0) {
    $input.val("0");
  }
  $input.val(setCurrencyFormat($input.val()));
}

function removeCurrency(event) {
  var $input = $(event.target);
  $input.val(removeCurrencyFormat($input.val()));
  if($input.val() == "0") {
    $input.val("");
  }
}

function setPercentage(event) {
  var $input = $(event.target);
  isPercentage(event);
  if($input.val().length == 0) {
    $input.val(0);
  }
  $input.val(setPercentageFormat($input.val()));
}

function removePercentage(event) {
  var $input = $(event.target);
  $input.val(removePercentageFormat($input.val()));
}
