$(document).ready(function() {
  tinyMCE.baseURL = "../tiny_mce/";
  
  tinymce.init({
    selector: "textarea",
    toolbar: "bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent"
  });
  
});