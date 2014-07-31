[#ftl]
[#assign title = "Permission denied!" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
<section class="content">
  <article class="fullContent">
      <h1>[@s.text name="server.error500.title" /]</h1>
    <div class="content">
      <p>[@s.text name="server.error500.description" /]</p>
    </div>
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]