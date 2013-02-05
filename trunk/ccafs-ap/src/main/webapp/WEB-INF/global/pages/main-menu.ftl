[#ftl]
<nav id="mainMenu">  
  <ul>
    <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>Home</li></a>
    [#if logged]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ]
        <a  href="javascript:void(0);" title="Disabled link" >
          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>Planning</li>
        </a>
      [/#if]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ]                
        <a href="${baseUrl}/reporting/activities.do" >
          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>Reporting</li>
        </a>
      [/#if]
      [#if currentUser.TL || currentUser.RPL || currentUser.admin ]
        <a href="javascript:void(0);" title="Disabled link" >
            <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>Summaries</li>
        </a>
      [/#if]
      [#if currentUser.admin ]                
        <a href="javascript:void(0);">
          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>Admin Area</li>
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