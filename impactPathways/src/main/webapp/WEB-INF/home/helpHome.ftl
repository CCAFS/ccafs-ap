[#ftl]
[#assign title = "Overview - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/helpHome.js"] /]

[#assign currentSection = "help" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  [#include "/WEB-INF/home/helpSubMenu.ftl" /]
  <article id="helpHome">
    <h1 class="">P&R Help</h1>
    <div class="borderBox"></div>
    
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]