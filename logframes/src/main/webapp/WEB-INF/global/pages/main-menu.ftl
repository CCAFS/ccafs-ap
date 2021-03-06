[#ftl]
<nav id="mainMenu">  
  <ul>
    [#if logged]
      [#-- Home element --]
      <a href="${baseUrl}/">
        <li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>
          <span class="icon">
            [#if currentSection?? && currentSection == "home"]
               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu-selected.png" />
            [#else]
               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu.png" />
            [/#if]
          </span>
          <span class="text">
            [@s.text name="menu.home" /]
          </span>
        </li>
      </a>
      
      [#-- Planning section --]
      [#if securityContext.CP || currentUser.TL || securityContext.RPL || currentUser.PI || securityContext.Admin ]
        [#if planningActive ]               
          <a  href="${baseUrl}/planning/activities.do">
        [#else]
          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
        [/#if]
          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>[@s.text name="menu.planning" /]</li>
        </a>
      [/#if]
      
      [#-- Reporting section --]
      [#if securityContext.CP || currentUser.TL || securityContext.RPL || currentUser.PI || securityContext.Admin ] 
        [#if reportingActive ]               
          <a href="${baseUrl}/reporting/introduction.do" >
        [#else]
          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
        [/#if]
          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>[@s.text name="menu.reporting" /]</li>
        </a>
      [/#if]
      
      [#-- Summaries section --]
      [#if currentUser.TL || securityContext.RPL || securityContext.Admin ]
        [#if summariesActive ]
          <a href="${baseUrl}/summaries/activities.do" /]" >
              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
          </a>
        [#else]
          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
          </a>
        [/#if]
      [/#if]
      
      [#-- Admin section --]
      [#if securityContext.Admin ]                
        <a href="javascript:void(0);">
          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>[@s.text name="menu.admin" /]</li>
        </a>
      [/#if]
    [#else]
      [#-- If the user is not logged show the login element in menu --]
      <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>[@s.text name="menu.login" /]</li></a>
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