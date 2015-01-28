$(document).ready(function(){
  // To improve the performance of the table load process
  // we should load the table information through a JSON response
  // or load it in a JS variable,
  // see this posts:
  // JSON response: http://madebyknight.com/optimizing-datatables-performance/
  // JS variable: http://ihatebugs.net/2014/04/how-to-increase-jquery-datatable-loading-speed/
  
  $('#deliverableList')
  .DataTable({
    "dom" : '<"top"f<"clear">>rt<"bottom"ip<"clear">>',
    "columnDefs" : [
        {
          "targets" : [
            1
          ],
          "visible" : false,
        }, {
          "targets" : [
            2
          ],
          "visible" : false
        }
    ],
    "drawCallback": function( settings ) {
      $('.hover-star').rating({
        cancel : 'Cancel',
        cancelValue : '0'
      });
    },
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
  
  // Initiate the deliverable ranking plugin
  $('.hover-star').rating({
    cancel : 'Cancel',
    cancelValue : '0'
  });
  
});

