[#ftl] 
<nav id="stageMenu" class="clearfix"> 
  <ul>  
    <li [#if partnerStage == "partnerLead"] class="currentSection" [/#if]>
      <a href="[@s.url action='partnerLead' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.partnerLead" /]
      </a>
    </li>
    <li [#if partnerStage == "ppaPartners"] class="currentSection" [/#if]>
      <a href="[@s.url action='ppaPartners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.ppaPartners" /] 
      </a>
    </li>
    <li [#if partnerStage == "partners"] class="currentSection" [/#if]>
      <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.partners" /]
      </a>
    </li> 
  </ul>
</nav> 