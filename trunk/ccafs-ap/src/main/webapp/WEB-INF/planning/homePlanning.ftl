[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery"] /]
[#assign currentSection = "planning" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<article>
  <div class="content">
    <h1>[@s.text name="planning.introduction.title" /]</h1>
    
    <div id="explanatoryNotes">
      <p class="fullBlock">[@s.text name="planning.introduction.part1" /]</p>
      <p class="fullBlock">[@s.text name="planning.introduction.part2" /]</p>
     </div>  
    <div class="fullBlock"></div>
    <p>
      <h6>[@s.text name="planning.introduction.startPlanning" /] <a class="popup" href="[@s.url action='activities' /]">[@s.text name="reporting.introduction.followLink" /]</a></h6>
    </p>
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]