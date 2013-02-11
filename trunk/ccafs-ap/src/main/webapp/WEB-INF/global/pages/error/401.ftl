[#ftl]
[#assign title = "Permission denied!" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<article>  
  <div class="content">
    <p>[@s.text name="server.error.401" /]</p>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]