Dropzone.autoDiscover = false;
$(document).ready(function(){
  
  attachEvents();
  // Create a dropzone to attach files
  addDropzone();
  // Check option selected
  checkOption();
  
  function attachEvents(){
    $("input[name^='dataSharingOptions']").on("click", checkOption);
  }
  
  function checkOption(){
    if ($("input#option-3").is(":checked")) {
      $("div#fileURL").hide();
      $("div#dragAndDrop").show();
    } else {
      $("div#dragAndDrop").hide();
      $("div#fileURL").show();
    }
  }
  
  function addDropzone(){
    $("div#dragAndDrop").dropzone({
      url : "/file/post"
    });
  }
  
});
