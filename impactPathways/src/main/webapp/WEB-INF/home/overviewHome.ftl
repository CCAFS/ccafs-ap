[#ftl]
[#assign title = "Overview - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject", "highcharts"] /]
[#assign customJS = ["${baseUrl}/js/home/overviewHome.js"] /]

[#assign currentSection = "overview" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article id="overviewHome">
  <h1 class="">P&R Overview</h1>
  <div class="borderBox">
    [#-- Deliverables By Type --]
    <div id="DeliverablesByType">
      <p>[@s.text name="home.overview.deliverablesByType"/]</p>
      ${jsonDeliverable}
    </div>
    [#-- Expected Deliverables Per Year --]
    <div id="ExpectedDeliverablesPerYear">
      <br/><br/>
      <p>[@s.text name="home.overview.expectedDeliverablesPerYear"/]</p>
      ${jsonexpectedDeliverable}
      <div id="graph"></div>
      <script language="javaScript">
      var expected = ${jsonexpectedDeliverable};
      
      
      </script>
    </div>
  </div>
  
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]