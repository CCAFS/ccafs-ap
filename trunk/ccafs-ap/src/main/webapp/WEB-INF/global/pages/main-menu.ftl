[#ftl]
<nav id="mainMenu">  
  <ul>
    <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>Home</li></a>
    [#if logged]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin() ]
        <a href="javascript:void(0);"><li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>Planning</li></a>                
      [/#if]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href="${baseUrl}/reporting/activities.do"><li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>Reporting</li></a>        
      [/#if]
      [#if currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]        
          <a href="javascript:void(0);"><li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>Summaries</li></a>
      [/#if]
      [#if currentUser.isAdmin()]
        <a href="javascript:void(0);"><li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>Admin Area</li></a>        
      [/#if]
    [/#if]
  </ul>
</nav>