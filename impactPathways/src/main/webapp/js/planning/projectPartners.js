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
      setInitialPPAPartners();
      initItemListEvents();
      validateEvent('[name=save], [name=next]', [
        "#justification"
      ]);

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

        // Activate the chosen plugin for new partners created
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
          $(element).find(".userId").attr("name", elementName + "user");
          $(element).find("[id$='partnerIndex']").html(index + 1);
          $(element).find("[id$='id']").attr("name", elementName + "id");
          $(element).find(".type").attr("name", elementName + "type");
          $(element).find("[id$='institution']").attr("name", elementName + "institution");
          $(element).find("[id$='responsabilities']").attr("name", elementName + "responsabilities");

          // Update index for CCAFS Partners
          $(element).find('.ppaPartnersList ul.list li').each(function(li_index,li) {
            $(li).find('.id').attr("name", elementName + "contributeInstitutions" + "[" + li_index + "].id");
          });
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

      function setInitialPPAPartners() {
        $("div.projectPartner").each(function(index,element) {
          // Getting PPA Partners previously selected by project partner
          var $select = $(element).find('select');
          $(element).find('li input.id').each(function(i_id,id) {
            $select.find('option[value=' + $(id).val() + ']').remove();
          });
          $select.trigger("liszt:updated");
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
          setProjectPartnersIndexes();
        });
      }

      function addItemList($option) {
        var $select = $option.parent();
        var $list = $option.parents('.panel').find('ul.list');
        var $li = $("#ppaListTemplate").clone(true).removeAttr("id");
        $li.find('.id').val($option.val());
        $li.find('.name').html($option.text());
        $li.appendTo($list).hide().show('slow');
        $option.remove();
        $select.trigger("liszt:updated");
        setProjectPartnersIndexes();
      }

      // Activate the chosen plugin to the countries, partner types and
      // partners lists.
      function addChosen() {
        $("form select").chosen({
          search_contains: true
        });
      }
    });
