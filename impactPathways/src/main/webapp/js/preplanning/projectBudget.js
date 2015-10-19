// Global VARS
var $allBudgetInputs, $overheadInputs, $CCAFSBudgetInputs, $linkedProjects, $selectAddProject, $plBudget;
var projectBudget,projectBudgetByYear,bilateralBudget,bilateralBudgetByYear;
var projectType;
var editable = true;

$(document).ready(init);

function init() {
  // Setting Global vars
  $partnerBudgetInputs = $("input.partnerBudget");
  $projectBudgetInputs = $("input.projectBudget");
  $genderBudgetInputs = $('input.projectGenderBudget');
  $overheadInputs = $("input[name$='bilateralCostRecovered']");
  $linkedProjects = $('#linkedProjects');
  $selectAddProject = $("select.addProject");
  $plBudget = $('.partnerLeader input.plBudget');

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
  $projectBudgetInputs.attr("autocomplete", "off").trigger("focusout");// .trigger("keyup");
  $partnerBudgetInputs.attr("autocomplete", "off").trigger("focusout");// .trigger("keyup");
  $genderBudgetInputs.attr("autocomplete", "off").trigger("focusout");// .trigger("keyup");

  // Validate justification and information
  validateEvent([
    "#justification"
  ]);

}

function attachEvents() {
  // Events for amount inputs
  $partnerBudgetInputs.on("keydown", isNumber).on("focusout", setCurrency).on("focus", removeCurrency).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
        projectBudget.calculateBudget();
        projectBudgetByYear.calculateBudget();
        bilateralBudget.calculateBudget();
        bilateralBudgetByYear.calculateBudget();
        calculateGenderBudget($(e.target).parents('.partnerBudget'));
        calculateProjectsBudgetRemaining(e);
  });

  // Events for amount inputs
  $projectBudgetInputs.on("keydown", isNumber).on("focusout", setCurrency).on("focus", removeCurrency).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
        bilateralBudget.calculateBudget();
        bilateralBudgetByYear.calculateBudget();
        calculateProjectsBudgetRemaining(e);
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
    if($(e.target).val() === "false") {
      $content.slideDown('slow');
    } else {
      $content.slideUp('slow');
    }
  });

  $selectAddProject.on('change', addLinkedProject);

  // Event to remove a linked project
  $('.remove').on('click', removeLinkedProject);

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
    $partnerBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    $genderBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    $projectBudgetInputs.each(function() {
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
    'url': '../../' + $('#projectsAction').val(),
      beforeSend: function() {
        $selectAddProject.empty().append(setOption(-1, "Please select a project"));
      },
      success: function(data) {
        // Getting projects previously selected
        var coreProjectsIds = [];
        $('#linkedProjects .budget input.budgetId').each(function(i_id,id) {
          coreProjectsIds.push($(id).val().toString());
        });
        // Setting projects allowed to select
        $.each(data.projects, function(i,project) {
          if($.inArray(project.id.toString(), coreProjectsIds) == -1) {
            $selectAddProject.append(setOption(project.id, "P"+project.id + " - " + project.title));
          }
        });
      },
      complete: function() {
        $selectAddProject.trigger("liszt:updated");
        // Regenerating hash from form information
        setFormHash();
      }
  });
}

function addLinkedProject(e){
  var $newItem = $('#projectBudget-template').clone(true).removeAttr('id');
  var item = new LinkedProjectObject($newItem);
  var $optionSelected = $(e.target).find('option:selected');
  item.setInfo($optionSelected.val(), $optionSelected.text());
  item.show();
  $optionSelected.remove();
  $selectAddProject.trigger("liszt:updated");
}

function removeLinkedProject(e){
  var item = new LinkedProjectObject($(e.target).parent());
  $selectAddProject.append(setOption(item.id, item.name)).trigger("liszt:updated");
  item.remove();
}

function setProjectsIndexes(){
  $linkedProjects.find('.budget').each(function(i,projectBudget){
    var item = new LinkedProjectObject($(projectBudget));
    item.setIndex(i);
  });
  $projectBudgetInputs = $("input.projectBudget");
}

function calculateProjectsBudgetRemaining(e){
  $plBudget.removeClass('fieldError');
  $(e.target).removeClass('fieldError');
  errorMessages = [];
  var totalProjectBudget = totalBudget($linkedProjects.find('input.budgetAmount'));
  if (totalProjectBudget >  removeCurrencyFormat($plBudget.val()) ){
    $plBudget.addClass('fieldError');
    $(e.target).addClass('fieldError');
    errorMessages.push($('#budgetCanNotExcced').val());
  }
}

function LinkedProjectObject(project){
  this.id= $(project).find('.budgetCofinancingProjectId').val();
  this.name= $(project).find('.title').text();
  this.setInfo= function(id,name){
    $(project).find('.budgetCofinancingProjectId').val(id);
    $(project).find('.linkedId').val(id);
    $(project).find('.title a').text(name);
    $(project).find('.budgetAmount').val(setCurrencyFormat(0));
  };
  this.setIndex = function (index){
    var elementName= "project.linkedProjects["+index+"].anualContribution.";
    $(project).find('.budgetId').attr('name', elementName+"id");
    $(project).find('.budgetYear').attr('name', elementName+"year");
    $(project).find('.budgetInstitutionId').attr('name', elementName+"institution.id");
    $(project).find('.budgetCofinancingProjectId').attr('name', elementName+"cofinancingProject.id");
    $(project).find('.budgetType').attr('name', elementName+"type");
    $(project).find('.budgetAmount').attr('name', elementName+"amount").attr('id', elementName+"amount");
  };
  this.remove = function (){
    $(project).slideUp("slow", function(){
      $(project).remove();
      setProjectsIndexes();
    });
  };
  this.show = function (){
    $(project).appendTo($linkedProjects).hide().show('slow');
    setProjectsIndexes();
  };
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
  var percentage = removePercentageFormat($(partnerBudget).find('input.projectGenderBudget').val())|| 0;
  var value = removeCurrencyFormat($(partnerBudget).find('input.partnerBudget').val())||0;
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
