$(document).ready(function() { 

  // character counter to description text areas.
  $("#outputsGroup").find("[id$='description']").each(function() {
    applyCharCounter($(this), 30000);
  });
  
});