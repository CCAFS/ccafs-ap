$(document).ready(
    function() {
      attachEvents();
      // This function enables launch the pop up window
      popups();
      // Activate the chosen plugin to the existing partners
      addChosen();
      if(!$("div.projectPartner").length) {
        $("a.addProjectPartner").trigger("click");
      }

      function attachEvents() {
        // Partners Events
        $("a.addProjectPartner").click(addPartnerEvent);
        $(".removePartner").click(removePartnerEvent);
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
      }

      function updateOrganizationsList(e) {
        var eventType = $(e.target).attr("class");
        var $parent = $(e.target).parent().parent().parent().parent().parent();
        if(eventType == "filters-link") {
          $parent = $(e.target).parent();
        }
        var $selectInstitutions = $parent.find("select[name$='partner']");
        var partnerTypes = $parent.find("select.partnerTypes").find('option:selected').val();
        var countryList = $parent.find("select.countryList").find('option:selected').val();
        var source =
            "../../json/institutionsByTypeAndCountry.do?institutionTypeID=" + partnerTypes + "&countryID="
                + countryList;

        if(eventType == "filters-link") {
          source = "../../json/institutionsByTypeAndCountry.do";
        }
        var institutionsList = "";

        $.getJSON(source, function(data) {
          $.each(data.institutions, function(index,institution) {
            institutionsList += "<option value=" + institution.id + ">" + institution.composedName + "</option>";
          });
        }).done(function() {
          $selectInstitutions.html(institutionsList);
        }).fail(function() {
          console.log("error");
        }).always(function() {
          $selectInstitutions.trigger("liszt:updated");
        });
      }

      // Partner Events
      function removePartnerEvent(e) {
        e.preventDefault();
        var $parent = $(e.target).parent().parent();
        $parent.hide("slow", function() {
          $parent.remove();
          setProjectPartnersIndexes();
        });
      }

      function addPartnerEvent(e) {
        e.preventDefault();
        var $newElement = $("#projectPartnerTemplate").clone(true).removeAttr("id").addClass("projectPartner");
        $(e.target).parent().before($newElement);
        $newElement.show("slow");

        // Activate the chosen plugin
        $newElement.find("select[name$='partner']").chosen({
            no_results_text: $("#noResultText").val(),
            search_contains: true
        });
        $newElement.find(".partnerTypes").chosen({
            allow_single_deselect: true,
            search_contains: true
        });
        $newElement.find(".countryList").chosen({
            allow_single_deselect: true,
            search_contains: true
        });
        setProjectPartnersIndexes();
      }

      function setProjectPartnersIndexes() {
        $("div.projectPartner").each(function(index,element) {
          var elementName = "project.projectPartners[" + index + "].";
          $(element).attr("id", "projectPartner-" + index);
          // CSS selector div[id$=parent] Get any DIV element where the ID attribute
          // value ends with "parent".
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
      function addChosen() {

        $("form select").chosen({
          search_contains: true
        });
/*
 * $("form select[name$='partner']").chosen({ search_contains: true }); $("form select[name$='institution']").chosen({
 * search_contains: true }); $("form .partnerTypes").chosen({ allow_single_deselect: true, search_contains: true });
 * $("form .ppaPartnersSelect").chosen({ allow_single_deselect: true, search_contains: true }); $("form
 * .countryList").chosen({ allow_single_deselect: true, search_contains: true });
 */
      }
      /*
       * ----------------------- Search users functions --------------------------
       */

      /* ----- Initialize ----- */

      var dialog;
      var timeoutID;
      var $elementSelected;
      var $dialogContent = $("#dialog-searchUsers");
      var dialogOptions = {
          autoOpen: false,
          height: 320,
          width: 500,
          modal: true,
          show: {
              effect: "size",
              duration: 100
          },
          buttons: {
            Cancel: function() {
              $(this).dialog("close");
              $dialogContent.find("form")[0].reset();
            }
          }
      };

      dialog = $dialogContent.dialog(dialogOptions);

      /* ----- Events ----- */

      // Event to open dialog box and search an contact person
      $(".searchUser").on("click", openSearchDialog);
      // Event when the user select the contact person
      $dialogContent.find("span.select").on("click", addUser);
      // Event to find an user according to search field
      $dialogContent.find(".search-content input").on("keyup", searchUsersEvent);
      $dialogContent.find(".search-button").on("click", function() {
        getData($('.search-input .input').val());
      });

      /* ----- Functions ----- */

      function openSearchDialog(e) {
        event.preventDefault();
        $elementSelected = $(this).parent();
        dialog.dialog("open");
        $dialogContent.find(".search-loader").fadeOut("slow");
        $dialogContent.find(".panel-body ul").empty();
      }

      function addUser(e) {
        var composedName = $(this).parent().find(".name").text();
        var userId = $(this).parent().find(".contactId").text();
        $elementSelected.find("input.userName").val(composedName).hide().fadeIn("slow");
        $elementSelected.find("input.userId").val(userId);
        dialog.dialog("close");
        $dialogContent.find("form")[0].reset();
      }

      function searchUsersEvent(e) {
        var query = $(this).val();
        if(timeoutID) {
          clearTimeout(timeoutID);
        }
        // Start a timer that will search when finished
        timeoutID = setTimeout(function() {
          getData(query);
        }, 500);
      }

      function getData(query) {
        if(query.length > 1) {
          $.ajax({
              'url': '../../searchUsers.do',
              'data': {
                q: query
              },
              'dataType': "json",
              beforeSend: function() {
                $dialogContent.find(".search-loader").show();
                $dialogContent.find(".panel-body ul").empty();
              },
              success: function(data) {
                var usersFound = (data.users).length;
                if(usersFound > 0) {
                  $dialogContent.find(".panel-body .userMessage").hide();
                  $.each(data.users, function(i,user) {
                    var $item = $dialogContent.find("li#userTemplate").clone(true).removeAttr("id");
                    $item.find('.name').html(escapeHtml(user.composedName));
                    $item.find('.contactId').html(user.id);
                    if(i == usersFound - 1) {
                      $item.addClass('last');
                    }
                    $dialogContent.find(".panel-body ul").append($item);
                  });
                } else {
                  $dialogContent.find(".panel-body .userMessage").show();
                }

              },
              complete: function() {
                $dialogContent.find(".search-loader").fadeOut("slow");
              }
          });
        }
      }

      function escapeHtml(text) {
        var map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return text.replace(/[&<>"']/g, function(m) {
          return map[m];
        });
      }

    });
