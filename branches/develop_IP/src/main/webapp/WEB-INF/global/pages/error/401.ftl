[#ftl]
[#assign title = "Permission denied!" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<section class="content">
  <meta http-equiv="refresh" content="10;url=[@s.url action='login' namespace="/" /]"> 
  <article class="fullContent">
      <h1>Error 401</h1>
    <div class="content">
      <p>[@s.text name="server.error.401" /]</p>
    </div>
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]