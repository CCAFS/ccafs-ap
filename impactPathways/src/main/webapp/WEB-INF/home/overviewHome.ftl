[#ftl]
[#assign title = "Overview - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/overviewHome.js"] /]

[#assign currentSection = "overview" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article id="overviewHome">
  <h1 class="">P&R Overview</h1>
  <div class="borderBox"></div>
  
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]