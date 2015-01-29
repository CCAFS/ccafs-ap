$(document).ready(function(){
  /*
   * $('#deliverableList').dataTable({ "bPaginate" : true, // This option enable the table pagination "bLengthChange" : false, // This option disables the select table size option "bFilter" : true, //
   * This option enable the search "bSort" : true, // this option enable the sort of contents by columns "bAutoWidth" : true, // This option enables the auto adjust columns width "iDisplayLength" : 20 //
   * Number of rows to show on the table });
   */
  $('#deliverableList').DataTable({
    "dom" : '<"top"f<"clear">>rt<"bottom"ip<"clear">>',
    initComplete : function(){
      var api = this.api();
      api.columns().indexes().flatten().each(function(i){
        var column = api.column(i);
        var select = $('<select><option value="">- Any -</option></select>').appendTo($("#filterBy .filter")[i]).on('change', function(){
          var val = $.fn.dataTable.util.escapeRegex($(this).val());
          column.search(val ? '^' + val + '$' : '', true, false).draw();
        });
        column.data().unique().sort().each(function(d,j){
          select.append('<option value="' + d + '">' + d + '</option>');
        });
        
      });
    }
  });
  
  
  $(".removeDeliverable").on("click", function(){
    return(confirm($("#removeConfirmation").val()));
  });
  
});
