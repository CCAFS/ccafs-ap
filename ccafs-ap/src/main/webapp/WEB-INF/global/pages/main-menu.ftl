[#ftl]
<nav id="mainMenu">  
  <ul>
    <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>[@s.text name="menu.home" /]</li></a>
    [#if logged]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ]
        <a  href="${baseUrl}/planning/activities.do">
          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>[@s.text name="menu.planning" /]</li>
        </a>
      [/#if]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ]                
        <a href="${baseUrl}/reporting/activities.do" >
          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>[@s.text name="menu.reporting" /]</li>
        </a>
      [/#if]
      [#if currentUser.TL || currentUser.RPL || currentUser.admin ]
        <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" >
            <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
        </a>
      [/#if]
      [#if currentUser.admin ]                
        <a href="javascript:void(0);">
          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>[@s.text name="menu.admin" /]</li>
        </a>
      [/#if]
    [/#if]
  </ul>
</nav>


<section id="generalMessages">
  [#-- Messages are going to show using notify plugin (see global.js) --]
  <ul id="messages" style="display: none;">
  [@s.iterator value="actionMessages"]    
    <li class="success">[@s.property escape="false" /]</li>    
  [/@s.iterator]
  [@s.iterator value="errorMessages"]    
    <li class="error">[@s.property escape="false" /]</li>    
  [/@s.iterator]
  </ul>
</section>