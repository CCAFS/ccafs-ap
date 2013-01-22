$(document).ready(function() {

  $(".addCaseStudies").click(function(event) {
    event.preventDefault();
    // Cloning tempalte.
    var $newCaseStudy = $("#caseStudy-999").clone(true);
    $("#addCaseStudiesBlock").before($newCaseStudy);

    // Jquery needs a second to update the dom, if dont give
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
  $("#casesStudiesBlock .caseStudy").each(
      function(index, caseStudy) {
        // Changing attributes of each component in order to match with the array order.
        // Main div.
        $(this).attr("id", "caseStudy-" + index);
        // Remove link.
        $(this).find("[id^='removeCaseStudy-']").attr("id",
            "removeCaseStudy-" + index);
        // case study identifier.
        $(this).find("[name$='id']").attr("name",
            "casesStudies[" + index + "].id");
        // title.
        $(this).find("[id$='title']").attr("id",
            "casesStudies[" + index + "].title");
        $(this).find("[name$='title']").attr("name",
            "casesStudies[" + index + "].title");
        $(this).find("[for$='title']").attr("for",
            "casesStudies[" + index + "].title");
        // Author.
        $(this).find("[id$='author']").attr("id",
            "casesStudies[" + index + "].author");
        $(this).find("[name$='author']").attr("name",
            "casesStudies[" + index + "].author");
        // Photo.
        $(this).find("[id$='photo']").attr("id",
            "casesStudies[" + index + "].photo");
        $(this).find("[name$='photo']").attr("name",
            "casesStudies[" + index + "].photo");
        $(this).find("[for$='photo']").attr("for",
            "casesStudies[" + index + "].photo");
        // Date.
        $(this).find("[id$='date']").attr("id",
            "casesStudies[" + index + "].date");
        $(this).find("[name$='date']").attr("name",
            "casesStudies[" + index + "].date");
        // Countries.
        $(this).find("[id$='countries']").attr("id",
            "casesStudies[" + index + "].countries");
        $(this).find("[name$='countries']").attr("name",
            "casesStudies[" + index + "].countries");
        // Keywords.
        $(this).find("[id$='keywords']").attr("id",
            "casesStudies[" + index + "].keywords");
        $(this).find("[name$='keywords']").attr("name",
            "casesStudies[" + index + "].keywords");
        // Objectives.
        $(this).find("[id$='objectives']").attr("id",
            "casesStudies[" + index + "].objectives");
        $(this).find("[name$='objectives']").attr("name",
            "casesStudies[" + index + "].objectives");
        // Description.
        $(this).find("[id$='description']").attr("id",
            "casesStudies[" + index + "].description");
        $(this).find("[name$='description']").attr("name",
            "casesStudies[" + index + "].description");
        // Results.
        $(this).find("[id$='results']").attr("id",
            "casesStudies[" + index + "].results");
        $(this).find("[name$='results']").attr("name",
            "casesStudies[" + index + "].results");
        // Partners.
        $(this).find("[id$='partners']").attr("id",
            "casesStudies[" + index + "].partners");
        $(this).find("[name$='partners']").attr("name",
            "casesStudies[" + index + "].partners");
        // Links.
        $(this).find("[id$='links']").attr("id",
            "casesStudies[" + index + "].links");
        $(this).find("[name$='links']").attr("name",
            "casesStudies[" + index + "].links");
      });
}