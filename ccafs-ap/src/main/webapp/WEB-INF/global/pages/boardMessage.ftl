[#ftl]
[#list boardMessages as bm]
  <div class="note [#if bm.urgent]note-urgent[/#if]"> 
      [#if bm.urgent]<img src="${baseUrl}/images/global/alert.gif"> [/#if] <p>${bm.message}</p>
  </div>
  
[/#list]
