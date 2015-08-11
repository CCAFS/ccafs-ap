[#ftl] 
<nav id="stageMenu" class="clearfix animated fadeInLeft"> 
  <ul>  
    <li [#if partnerStage == "partnerLead"] class="currentSection" [/#if]>
      <a href="[@s.url action='partnerLead'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.partnerLead" /]
      </a>
    </li>
    [#if !project.bilateralProject]
    <li [#if partnerStage == "ppaPartners"] class="currentSection" [/#if]>
      <a href="[@s.url action='ppaPartners'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.ppaPartners" /] 
      </a>
    </li>
    [/#if]
    <li [#if partnerStage == "partners"] class="currentSection" [/#if]>
      <a href="[@s.url action='partners'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        [@s.text name="planning.projectPartners.subMenu.partners" /]
      </a>
    </li> 
  </ul>
</nav> 