$(document).ready(function(){
  console.log("Deliverables Data Sharing");
  
  $("div#dragAndDrop").dropzone({
    url : "/file/post"
  });
  
});
