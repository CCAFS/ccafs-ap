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

        $('.ppaPartnersList select').on('change', function(e) {
          addItemList($(this).find('option:selected'));
        });

        $('ul li .remove').on('click', function(e) {
          removeItemList($(this).parents('li'));
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

      function removeItemList($item) {
        // Adding to select list
        var $select = $item.parents('.panel').find('select');
        $select.append(setOption($item.find('.id').val(), $item.find('.name').text()));
        $select.trigger("liszt:updated");
        // Removing from list
        $item.hide("slow", function() {
          $item.remove();
        });
        setProjectPartnersIndexes();
      }

      function addItemList($option) {
        var $select = $option.parent();
        var $list = $option.parents('.panel').find('ul.list');
        var $li = $("#ppaListTemplate").clone(true).removeAttr("id");
        $li.find('.id').val($option.val());
        $li.find('.name').html($option.text());
        $list.prepend($li);
        $option.remove();
        $select.trigger("liszt:updated");
        setProjectPartnersIndexes();
      }

      function addPartnerEvent(e) {
        e.preventDefault();
        var $newElement = $("#projectPartnerTemplate").clone(true).removeAttr("id").addClass("projectPartner");
        $(e.target).parent().before($newElement);
        $newElement.show("slow");

        // Activate the chosen plugin
        $newElement.find("select").chosen({
            no_results_text: $("#noResultText").val(),
            search_contains: true
        });

        setProjectPartnersIndexes();
      }

      function setProjectPartnersIndexes() {
        $("div.projectPartner").each(function(index,element) {
          var elementName = $('#partners-name').val() + "[" + index + "].";
          $(element).attr("id", "projectPartner-" + index);
          // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
          $(element).find("[id$='partnerIndex']").html(index + 1);
          $(element).find("[id$='id']").attr("name", elementName + "id");
          $(element).find("[id$='institution']").attr("name", elementName + "institution");
          $(element).find(".userId").attr("name", elementName + "projectLeader");
          $(element).find("[id$='responsabilities']").attr("name", elementName + "responsabilities");

          // Update index for CCAFS Partners
          $(element).find('.ppaPartnersList ul.list li').each(function(li_index,li) {
            $(li).find('.id').attr("name", elementName + "contributeInstitutions" + "[" + li_index + "].id");
          });
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
      /**
       * ----------------------- Search users functions --------------------------
       */

      /* ----- Initialize ----- */

      var dialog;
      var timeoutID;
      var $elementSelected;
      var $dialogContent = $("#dialog-searchUsers");
      var dialogOptions = {
          autoOpen: false,
          height: 400,
          width: 600,
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

      // Initializing Manage users dialog
      dialog = $dialogContent.dialog(dialogOptions);

      // Loading initial data with all users
      getData('');

      /* ----- Events ----- */

      // Event for manage the accordion function
      $dialogContent.find(".accordion").on('click', function() {
        $(this).parent().find('.accordion span').removeClass('ui-icon-triangle-1-s').addClass('ui-icon-triangle-1-e');
        $(this).siblings('.accordion-block').hide('slow');
        $(this).next().slideToggle();
        $(this).find('span').addClass('ui-icon-triangle-1-s');
      });

      // Event to open dialog box and search an contact person
      $(".searchUser").on("click", openSearchDialog);

      // Event when the user select the contact person
      $dialogContent.find("span.select").on("click", function() {
        var userId = $(this).parent().find(".contactId").text();
        var composedName = $(this).parent().find(".name").text();
        // Add user
        addUser(composedName, userId);
      });

      // Event to find an user according to search field
      $dialogContent.find(".search-content input").on("keyup", searchUsersEvent);

      // Event to search users clicking in "Search" button
      $dialogContent.find(".search-button").on("click", function() {
        getData($('.search-input .input').val());
      });

      // Trigger to open create user section
      $dialogContent.find("span.link").on("click", function() {
        $dialogContent.find("#create-user").trigger('click');
      });

      // Event to Create an user clicking in the button
      $dialogContent.find(".create-button").on("click", function() {
        $dialogContent.find('.warning-info').empty().hide();
        var invalidFields = [];
        var user = {};
        if($dialogContent.find("#isCCAFS").is(':checked')) {
          user.firstName = $dialogContent.find("#firstName").val();
          user.lastName = $dialogContent.find("#lastName").val();
        }
        user.email = $dialogContent.find("#email").val().trim();
        user.isActive = $dialogContent.find("#isActive").val();

        // Validate if fields are filled
        $.each(user, function(key,value) {
          if(value.length < 1) {
            invalidFields.push($('label[for="' + key + '"]').text().trim().replace(':', ''));
          }
        });
        // Validate Email
        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        if(!emailReg.test(user.email)) {
          invalidFields.push('valid email');
        }

        if(invalidFields.length > 0) {
          var msj = "You must fill " + invalidFields.join(', ');
          $dialogContent.find('.warning-info').text(msj).fadeIn('slow');
        } else {
          $.ajax({
              'url': '../../createUser.do',
              data: user,
              beforeSend: function() {
                $dialogContent.find('.loading').show();
              },
              success: function(data) {
                if(data.message) {
                  $dialogContent.find('.warning-info').text(data.message).fadeIn('slow');
                } else {
                  addUser(data.newUser.composedName, data.newUser.id);
                }
              },
              complete: function(data) {
                $dialogContent.find('.loading').fadeOut();
              }
          });
        }

      });

      $dialogContent.find("form").on("submit", function(e) {
        event.preventDefault();
      });

      /* ----- Functions ----- */

      function openSearchDialog(e) {
        e.preventDefault();
        $elementSelected = $(this).parent();
        dialog.dialog("open");
        $dialogContent.find(".search-loader").fadeOut("slow");
      }

      function addUser(composedName,userId) {
        $elementSelected.find("input.userName").val(composedName).hide().fadeIn("slow");
        $elementSelected.find("input.userId").val(userId);
        dialog.dialog("close");
        $dialogContent.find("form")[0].reset();
      }

      function searchUsersEvent(e) {
        var query = $(this).val();
        if(query.length > 1) {
          if(timeoutID) {
            clearTimeout(timeoutID);
          }
          // Start a timer that will search when finished
          timeoutID = setTimeout(function() {
            getData(query);
          }, 500);
        } else {
          getData('');
        }

      }

      function getData(query) {
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

    });
