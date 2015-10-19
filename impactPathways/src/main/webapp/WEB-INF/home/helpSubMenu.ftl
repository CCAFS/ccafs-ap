[#ftl]
[#-- Submit controller --]
<script src="${baseUrl}/js/home/helpHome.js"></script>

[#assign currCss= "currentSection"]


<nav id="secondaryMenu" class="projectMenu">
<h1><center> 

</h1></center>
  [#--]<h3> <a class="goBack"  href="[@s.url namespace='/planning' action='projectsList'][/@s.url]"> [@s.text name="planning.project" /] Menu</a></h3>--]
  <ul> 
    <li class="currentSection">
      <p>[@s.text name="home.help.menu" /]</p>
      <ul>
        <li>[@s.text name="home.help.menu" /]</li>
        <li>[@s.text name="home.help.templates" /]</li>
        <li>[@s.text name="home.help.dmsp" /]</li>
        <li>[@s.text name="home.help.faq" /]</li>
        <li>[@s.text name="home.help.glossary" /]</li>
        [#--[@menu actionName="userManual" stageName="userManual" textName="home.help.menu.userManual"/] 
        [@menu actionName="templates" stageName="templates" textName="home.help.menu.templates"/] 
        [@menu actionName="dmsp" stageName="dmsp" textName="home.help.menu.dmsp"/]
        [@menu actionName="faq" stageName="faq" textName="home.help.menu.faq"/] 
        [@menu actionName="glossary" stageName="glossary" textName="home.help.menu.glossary"/]--]  
      </ul>
    </li>
  </ul>
  <br />
  
  
</nav>
