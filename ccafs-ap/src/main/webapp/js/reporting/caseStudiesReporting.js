$(document).ready(function() {

  $(".addCaseStudies").click(function(event) {
    event.preventDefault();
    // Cloning tempalte.
    var $newCaseStudy = $("#caseStudy-999").clone(true);
    $("#addCaseStudiesBlock").before($newCaseStudy);

    // Jquery needs a second to update the dom, if you dont give
    // that time the rename function dont take the remove change
    // the smaller time to take the change is 578
    setTimeout(renameCaseStudies, 600);

    $newCaseStudy.fadeIn("slow");
  });

  $('.removeCaseStudy').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#caseStudy-" + removeId).fadeOut("slow");
    // removing div.
    $("#caseStudy-" + removeId).hide("slow", function() {
      $(this).remove();
    });

    // Jquery needs a second to update the dom, if dont give
    // that time the rename function dont take the remove change
    // the smaller time to take the change is 578
    setTimeout(renameCaseStudies, 600);
  });

});

function renameCaseStudies() {
  // getting the number of expected deliverables.
  $("#caseStudiesBlock .caseStudy").each(
      function(index, caseStudy) {
        // Changing attributes of each component in order to match with the array order.
        // Main div.
        $(this).attr("id", "caseStudy-" + index);
        // Remove link.
        $(this).find("[id^='removeCaseStudy-']").attr("id",
            "removeCaseStudy-" + index);
        // case study identifier.
        $(this).find("[name$='id']").attr("name",
            "caseStudies[" + index + "].id");
        // title.
        $(this).find("[id$='title']").attr("id",
            "caseStudies[" + index + "].title");
        $(this).find("[name$='title']").attr("name",
            "caseStudies[" + index + "].title");
        $(this).find("[for$='title']").attr("for",
            "caseStudies[" + index + "].title");
        // Author.
        $(this).find("[id$='author']").attr("id",
            "caseStudies[" + index + "].author");
        $(this).find("[name$='author']").attr("name",
            "caseStudies[" + index + "].author");
        // Image.
        $(this).find("[id$='image']").attr("id",
            "caseStudies[" + index + "].image");
        $(this).find("[name$='image']").attr("name",
            "caseStudies[" + index + "].image");
        $(this).find("[for$='image']").attr("for",
            "caseStudies[" + index + "].image");
        // Date.
        $(this).find("[id$='date']").attr("id",
            "caseStudies[" + index + "].date");
        $(this).find("[name$='date']").attr("name",
            "caseStudies[" + index + "].date");
        // Countries.
        $(this).find("[id$='countries']").attr("id",
            "caseStudies[" + index + "].countries");
        $(this).find("[name$='countries']").attr("name",
            "caseStudies[" + index + "].countries");
        // Keywords.
        $(this).find("[id$='keywords']").attr("id",
            "caseStudies[" + index + "].keywords");
        $(this).find("[name$='keywords']").attr("name",
            "caseStudies[" + index + "].keywords");
        // Objectives.
        $(this).find("[id$='objectives']").attr("id",
            "caseStudies[" + index + "].objectives");
        $(this).find("[name$='objectives']").attr("name",
            "caseStudies[" + index + "].objectives");
        // Description.
        $(this).find("[id$='description']").attr("id",
            "caseStudies[" + index + "].description");
        $(this).find("[name$='description']").attr("name",
            "caseStudies[" + index + "].description");
        // Results.
        $(this).find("[id$='results']").attr("id",
            "caseStudies[" + index + "].results");
        $(this).find("[name$='results']").attr("name",
            "caseStudies[" + index + "].results");
        // Partners.
        $(this).find("[id$='partners']").attr("id",
            "caseStudies[" + index + "].partners");
        $(this).find("[name$='partners']").attr("name",
            "caseStudies[" + index + "].partners");
        // Links.
        $(this).find("[id$='links']").attr("id",
            "caseStudies[" + index + "].links");
        $(this).find("[name$='links']").attr("name",
            "caseStudies[" + index + "].links");
      });
}