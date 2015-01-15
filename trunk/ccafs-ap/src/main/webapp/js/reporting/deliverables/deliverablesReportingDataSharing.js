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
      $("div#ccafsFiles").show();
    } else {
      if ($("#filesUploaded ul").children().length) {
        console.log("If you change to this option, the files uploaded will be deleted");
      }
      $("div#ccafsFiles").hide();
      $("div#fileURL").show();
    }
  }
  
  function addDropzone(){
    $("div#dragAndDrop").dropzone({
      init : initDropzone,
      fallback : fallBackDropzone,
      uploadMultiple : true,
      paramName : "file", // The name that will be used to transfer the file
      params : {
        activityID : $("input[name^='activityID']").val(),
        deliverableID : $("input[name^='deliverableID']").val()
      },
      url : "uploadDeliverable.do",
      maxFilesize : 30
    });
  }
  
  function initDropzone(){
    this.on("success", function(file){
      console.log(file);
      var $newElement = $("#deliberableFileTemplate").clone(true).removeAttr("id");
      $newElement.find(".fileName").html(file.name);
      $newElement.find(".fileFormat").html(file.type);
      $newElement.find(".fileSize").html((file.size / 1024).toFixed(1) + " KB");
      $("#filesUploaded ul").append($newElement);
      $newElement.show("slow");
      this.removeFile(file);
    });
  }
  
  function fallBackDropzone(){
    $("#addFileInput").on("click", function(){
      var $newElement = $("#fileInputTemplate").clone(true).removeAttr("id");
      $(this).parent().prepend($newElement);
      $newElement.fadeIn();
    });
    $(".removeInput").on("click", function(){
      $(this).parent().remove();
    });
    $("#addFileInput").trigger("click", true);
  }
  
});
