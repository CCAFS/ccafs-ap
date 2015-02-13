[#ftl]
[#list boardMessages as bm]

  [#if bm.urgent]
    <p class="note-urgent">
  [#else]
    <p class="note">
  [/#if]
      ${bm.message}
  </p>
  
[/#list]
