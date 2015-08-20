jQuery.fn.dataTableExt.aTypes.unshift(function(sData) {
  sData = typeof sData.replace == 'function' ? sData.replace(/<[\s\S]*?>/g, "") : sData;
  sData = $.trim(sData);

  var sValidFirstChars = "0123456789-";
  var sValidChars = "0123456789.";
  var Char;
  var bDecimal = false;

  /* Check for a valid first char (no period and allow negatives) */
  Char = sData.charAt(0);
  if(sValidFirstChars.indexOf(Char) == -1) {
    return null;
  }

  /* Check all the other characters are valid */
  for(var i = 1; i < sData.length; i++) {
    Char = sData.charAt(i);
    if(sValidChars.indexOf(Char) == -1) {
      return null;
    }

    /* Only allowed one decimal place... */
    if(Char == ".") {
      if(bDecimal) {
        return null;
      }
      bDecimal = true;
    }
  }

  return 'num-html';
});

jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "num-html-pre": function(a) {
      var x = String(a).replace(/<[\s\S]*?>/g, "");
      return parseFloat(x);
    },

    "num-html-asc": function(a,b) {
      return((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "num-html-desc": function(a,b) {
      return((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});

$(document).ready(function() {
  $('table.projectsList').dataTable({
      "bPaginate": true, // This option enable the table pagination
      "bLengthChange": true, // This option disables the select table size option
      "bFilter": true, // This option enable the search
      "bSort": true, // this option enable the sort of contents by columns
      "bAutoWidth": false, // This option enables the auto adjust columns width
      "iDisplayLength": 15, // Number of rows to show on the table
      "fnDrawCallback": function() {
        // This function locates the add activity button at left to the filter box
        var table = $(this).parent().find("table");
        if($(table).attr("id") == "currentActivities") {
          $("#currentActivities_filter").prepend($("#addActivity"));
        }
      },
      aoColumnDefs: [
          {
              bSortable: false,
              aTargets: [
                  -1, -2, -3,
              ]
          }, {
              sType: "num-html",
              aTargets: [
                0
              ]
          }
      ]
  });
  $('table.projectsList').on('draw.dt', function() {
    $("a.removeProject").on("click", removeProject);
  });

  $("#submitForm").on("submit", function(evt) {
    if(confirm($("#beforeSubmitMessage").val())) {
      return true;
    } else {
      evt.preventDefault();
      return false;
    }
  });

  $('.loadingBlock').hide().next().fadeIn(500);

  addJustificationPopUp();
});

// Justification popup global vars
var dialog, projectId;
var $dialogContent;

function addJustificationPopUp() {
  $dialogContent = $("#dialog-justification");
  // Initializing justification dialog
  dialog = $dialogContent.dialog(dialogOptions = {
      autoOpen: false,
      height: 200,
      width: 400,
      modal: true,
      buttons: {
          Cancel: function() {
            $(this).dialog("close");
          },
          Remove: function() {
            var $justification = $dialogContent.find("#justification");
            if($justification.val().length > 0) {
              $justification.removeClass('fieldError');
              $dialogContent.find("form").submit();
            } else {
              $justification.addClass('fieldError');
            }
          }
      },
  });
  // Event to open dialog to remove deliverable
  $("a.removeProject").on("click", removeProject);
}

function removeProject(e) {
  e.preventDefault();
  $dialogContent.find("#justification").val('').removeClass('fieldError');
  // Getting deliverable ID and setting input hidden to remove that deliverable
  $dialogContent.find('input[name$=projectID]').val($(e.target).parent().attr('id').split('-')[1]);
  dialog.dialog("open");
}