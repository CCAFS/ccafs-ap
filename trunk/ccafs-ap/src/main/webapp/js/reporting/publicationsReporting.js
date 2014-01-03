$(document).ready(function() {  
  
  function renamePublications() {
    // getting the text of the index element.
    var itemText = $("#template").find(".itemIndex").text();
    $("#items .publication").each(function(index, activityPartner) {
          // Changing attributes of each component in order to match
          // with the array order.
          // Main div.
          $(this).attr("id", "publication-" + index);
          //Item index
          $(this).find(".itemIndex").text(itemText + " " + (index+1));
          // Remove link.
          $(this).find("[id^='removePublication-']").attr("id","removePublication-" + index);
          // Publication id.
          $(this).find("[name$='id']").attr("name","publications[" + index + "].id");
          // Publication Type.
          $(this).find("[id$='type']").attr("id", "publications_publications_" + index + "__type");
          $(this).find("[name$='type']").attr("name", "publications[" + index + "].type");
          // Publication Identifier.
          $(this).find("[id$='identifier']").attr("id", "publications[" + index + "].identifier");
          $(this).find("[name$='identifier']").attr("name", "publications[" + index + "].identifier");
          $(this).find("[for$='identifier']").attr("for", "publications[" + index + "].identifier");
          // Publication Access.
          $(this).find("[name$='access']").attr("id", "publications[" + index + "].access");
          $(this).find("[name$='access']").attr("name", "publications[" + index + "].access");
          // Publication Citation.
          $(this).find("[id$='citation']").attr("id", "publications[" + index + "].citation");
          $(this).find("[name$='citation']").attr("name", "publications[" + index + "].citation");
          $(this).find("[for$='citation']").attr("for", "publications[" + index + "].citation");
          // Themes.
          $(this).find("[name$='relatedThemes']").each(function(index2) {
            if( ! $(this).is(":hidden") ){
              $(this).attr("id", "publications[" + index + "].relatedThemes-" + index2);
              $(this).attr("name", "publications[" + index + "].relatedThemes");
              $(this).next(".checkboxLabel").attr('for', "publications[" + index + "].relatedThemes-" + index2);
            }else{
              $(this).attr("id", "__multiselect_publications_publications_" + index + "__relatedThemes");
              $(this).attr("name", "__multiselect_publications[" + index + "].relatedThemes");
            }
          });
          // Publication File URL.
          $(this).find("[id$='fileUrl']").attr("id", "publications[" + index + "].fileUrl");
          $(this).find("[name$='fileUrl']").attr("name", "publications[" + index + "].fileUrl");
          $(this).find("[for$='fileUrl']").attr("for", "publications[" + index + "].fileUrl");
          // Publication indicators
          $(this).find("[id$='isiPublication']").attr("id", "publications[" + index + "].isiPublication");
          $(this).find("[id$='isiPublication']").attr("name", "publications[" + index + "].isiPublication");
          $(this).find("[id$='narsCoauthor']").attr("id", "publications[" + index + "].narsCoauthor");
          $(this).find("[id$='narsCoauthor']").attr("name", "publications[" + index + "].narsCoauthor");
          $(this).find("[id$='earthSystemCoauthor']").attr("id", "publications[" + index + "].earthSystemCoauthor");
          $(this).find("[id$='earthSystemCoauthor']").attr("name", "publications[" + index + "].earthSystemCoauthor");
        }
    );
  }

  // This event show or hide the access type option according to the 
  // type of publication.
  $("[name$='type']").change(function(event) {
    event.preventDefault();
    // Get the element id
    var elementId = $(event.target).attr("id").split("_")[2];
    
    // If the type is 1 (Journal papers) the options are showed
    if($(event.target).val() == 1){
      $("#publication-" + elementId).find(".accessType").show("slow");
      $("#publication-" + elementId).find(".journalArticlesIndicators").show("slow");
    }else{
      $("#publication-" + elementId).find(".accessType").hide("slow", function() {
        // remove radio button selection
        $(this).find(".radiosList").find(":checked").attr('checked', false);        
      });
      
      $("#publication-" + elementId).find(".journalArticlesIndicators").hide("slow", function() {
        // remove radio button selection
        $(this).find(".checkbox").find(":checked").attr('checked', false);        
      });
    }
  });
  
  $(".addPublication").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newPublication = $("#publication-9999").clone(true);
    $(".addLink").before($newPublication);
    $(".addLink").before("<hr />");
    $newPublication.fadeIn("slow");
    renamePublications();
  });

  $('.removePublication').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#publication-" + removeId).hide("slow", function() {
      // removing division line.
      $(this).next("hr").remove();
      // removing div.
      $(this).remove();
      renamePublications();
    });
  });  
});
