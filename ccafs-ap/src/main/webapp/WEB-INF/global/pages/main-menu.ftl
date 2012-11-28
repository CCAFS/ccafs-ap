[#ftl]
<nav id="mainMenu">
  <ul>
    <a href="${baseUrl}/"><li>Home</li></a>
    [#if logged]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin() ]
        <!--a href="${baseUrl}/"><li a [#if currentSection == "planning"] class="currentSection" [/#if]>Planning</li></a -->                
      [/#if]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href="${baseUrl}/reporting/activities.do"><li [#if currentSection == "reporting"] class="currentSection" [/#if]>Reporting</li></a>        
      [/#if]
      [#if currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]        
          <a href="${baseUrl}/"><li [#if currentSection == "summaries"]class="currentSection"[/#if]>Summaries</li></a>
      [/#if]
      [#if currentUser.isAdmin()]
        <a href="${baseUrl}/"><li [#if currentSection == "admin"] class="currentSection" [/#if]>Admin Area</li></a>        
      [/#if]
    [/#if]
  </ul>
</nav>