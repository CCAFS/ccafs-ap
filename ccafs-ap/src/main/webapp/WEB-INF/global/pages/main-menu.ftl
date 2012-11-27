[#ftl]
<nav id="mainMenu">
  <ul>
    <a href="${baseUrl}/"><li>Home</li></a>
    [#if logged]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin() ]
        <!-- a href="${baseUrl}/"><li>Planning</li></a -->
      [/#if]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href="${baseUrl}/reporting/activities.do"><li>Reporting</li></a>
      [/#if]
      [#if currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href="${baseUrl}/"><li>Summaries</li></a>
      [/#if]
      [#if currentUser.isAdmin()]
        <a href="${baseUrl}/"><li>Admin Area</li></a>
      [/#if]
    [/#if]
  </ul>
</nav>