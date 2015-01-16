Dropzone.autoDiscover = false;
$(document).ready(function(){
  
  attachEvents();
  // Create a dropzone to attach files
  addDropzone();
  // Check option selected
  checkOption();
  
  function attachEvents(){
    // This event is for check if files smaller than 30MB will be hosted in CCAFS
    $("#dataSharingOptions input[type=radio]").on("click", checkOption);
    // This event is for remove file upload fields and inputs
    $(".removeInput").on("click", removeFileUploaded);
    // This event is when will be add one URL
    $(".addFileURL").on("click", addfileURL);
  }
  
  function removeFileUploaded(){
    $(this).parent().fadeOut(function(){
      $(this).remove();
      setDeliverableFilesIndexes();
    });
    
  }
  
  function checkOption(){
    var $optionSelectd = $('#dataSharingOptions input[type=radio]:checked');
    $(".uploadBlock").hide();
    $optionSelectd.parent().find(".uploadBlock").fadeIn();
  }
  
  function addDropzone(){
    $("div#dragAndDrop").dropzone({
      init : initDropzone,
      fallback : fallBackDropzone, // Run this function if the browser not support dropzone plugin
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
    this.on("success", function(file,done){
      var result = jQuery.parseJSON(done);
      file.hosted = "Locally";
      file.fileID = result.fileID;
      addFileToUploaded(file);
      this.removeFile(file);
    });
  }
  
  function addfileURL(e){
    var $target = $(e.target).parent();
    var $parent = $target.parent();
    var urlString = $target.find("input").val();
    if (checkUrl(urlString)) {
      var file = {
        "name" : urlString,
        "hosted" : $parent.find("input[type=radio]").val(),
        fileID : -1
      };
      addFileToUploaded(file);
      $target.find("input").val("http://");
    } else {
      alert("Invalid URL");
    }
  }
  
  // This function run when the browser not support dropzone plugin
  function fallBackDropzone(){
    $("#addFileInput").on("click", function(){
      var $newElement = $("#fileInputTemplate").clone(true).removeAttr("id");
      $(this).parent().prepend($newElement);
      $newElement.fadeIn();
    });
    $("#addFileInput").trigger("click", true);
  }
  
  function addFileToUploaded(file){
    console.log(file);
    var $newElement = $("#deliverableFileTemplate").clone(true).removeAttr("id");
    
    // Filling information obtained
    $newElement.find(".fileID").val(file.fileID);
    if (file.hosted == "Locally") {
      $newElement.find("input[type='hidden'].fileHosted").remove();
      $newElement.find("input[type='hidden'].fileLink").remove();
      $newElement.find(".fileSize").html((file.size / 1024).toFixed(1) + " KB");
    }
    $newElement.find(".fileHosted").val(file.hosted);
    $newElement.find(".fileLink").val(file.name);
    console.log((file.name).length);
    if ((file.name).length > 70)
      file.name = (file.name).substring(0, 70) + "...";
    
    $newElement.find(".fileName").html(file.name);
    $newElement.find(".fileFormat").html(file.hosted);
    
    $("#filesUploaded ul").prepend($newElement);
    $newElement.show("slow");
    setDeliverableFilesIndexes();
  }
  
  function setDeliverableFilesIndexes(){
    $("form .fileUploaded").each(function(i,element){
      var elementName = "deiverableFile[" + i + "].";
      $(element).find("input[type='hidden'].fileID").attr("name", elementName + "id");
      $(element).find("input[type='hidden'].fileHosted").attr("name", elementName + "hosted");
      $(element).find("input[type='hidden'].fileLink").attr("name", elementName + "link");
      
    });
  }
  
  function checkUrl(url){
    return url.match(/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/);
  }
  
});
