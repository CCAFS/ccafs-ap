$(document).ready(function() {

  // Organize the index of keywords elements
  organizeActivityKeyword();

  /* ------ Keywords events ---------- */

  $(".removeKeyword").on("click", function(event) {
    event.preventDefault();
    $(".keyword").last().fadeOut(function() {
      $(this).remove();
      organizeActivityKeyword();
    });

    // If there is no more elements, hide the remove option
    if ($(".keyword").length == 0) {
      $("#removeKeywordBlock").fadeOut();
    }
  });

  $(".addKeyword").on("click", function(event) {
    event.preventDefault();
    var newObjective = $("#keywordTemplate").clone(true);
    $(newObjective).addClass("activityKeyword");
    $(newObjective).addClass("keyword");
    $("#keywordsList").append(newObjective);
    organizeActivityKeyword();
    newObjective.show("slow");
  });

  /* ------ Other Keywords events ---------- */

  $(".removeOtherKeyword").on("click", function(event) {
    event.preventDefault();
    $(".other").last().fadeOut(function() {
      $(this).remove();
      organizeActivityKeyword();
    });

    // If there is no more elements, hide the remove option
    if ($(".other").length == 0) {
      $("#removeOtherKeywordBlock").fadeOut();
    }
  });

  $(".addOtherKeyword").on("click", function(event) {
    event.preventDefault();
    var newObjective = $("#otherKeywordTemplate").clone(true);
    $(newObjective).addClass("activityKeyword");
    $(newObjective).addClass("other");
    $("#otherKeywordsList").append(newObjective);
    organizeActivityKeyword();
    newObjective.show("slow");
  });

  /* ------ Resources events ---------- */

  $(".removeResource").on("click", function(event) {
    event.preventDefault();
    $(".resource").last().fadeOut(function() {
      $(this).remove();
    });

    // If there is no more elements, hide the remove option
    if ($(".resource").length == 0) {
      $("#removeResourceBlock").fadeOut();
    }
  });

  $(".addResource").on("click", function(event) {
    event.preventDefault();
    var newObjective = $("#resourceTemplate").clone(true);
    $(newObjective).addClass("resource");
    $("#resourcesList").append(newObjective);
    renameResources();
    newObjective.show("slow");
  });

});

function organizeActivityKeyword() {
  renameKeywords();
  renameOtherKeywords();
}

function renameKeywords() {
  $(".activityKeyword").each(
      function(index, objective) {
        if ($(this).hasClass("keyword")) {
          // Block id
          $(this).attr("id", "keyword-" + index);
          // Description
          $(this).find(".keywords").attr("name",
              "activity.keywords[" + index + "].keyword");
          $(this).find(".keywords").attr("id",
              "activity.keywords[" + index + "].keyword");
          // Hidden id
          $(this).find(".activityKeywordId").attr("name",
              "activity.keywords[" + index + "].id");
        }
      });

  // If it is the first option show the remove option again 
  if ($(".keyword").length == 1) {
    $("#removeKeywordBlock").fadeIn();
  }
}

function renameOtherKeywords() {
  $(".activityKeyword").each(
      function(index, objective) {
        //Check if is other keyword or keyword
        if ($(this).hasClass("other")) {
          // Block id
          $(this).attr("id", "otherKeyword-" + index);
          // Description
          $(this).find("[name$='other']").attr("name",
              "activity.keywords[" + index + "].other");
          $(this).find("[name$='other']").attr("id",
              "activity.keywords[" + index + "].other");
          // Hidden id
          $(this).find(".activityKeywordId").attr("name",
              "activity.keywords[" + index + "].id");
        }
      });

  // If it is the first option show the remove option again 
  if ($(".other").length == 1) {
    $("#removeOtherKeywordBlock").fadeIn();
  }
}

function renameResources() {
  $(".resource").each(
      function(index, objective) {
        // Block id
        $(this).attr("id", "resource-" + index);
        // Hidden id
        $(this).find("[name$='resourceId']").attr("name",
            "activity.resources[" + index + "].id");
        // Description
        $(this).find("[name$='name']").attr("name",
            "activity.resources[" + index + "].name");
      });

  // If it is the first option show the remove option again 
  if ($(".resource").length == 1) {
    $("#removeResourceBlock").fadeIn();
  }
}