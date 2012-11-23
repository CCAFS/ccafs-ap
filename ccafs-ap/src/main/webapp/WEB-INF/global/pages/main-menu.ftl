[#ftl]
<nav id="mainMenu">
  <ul>
    <a href=""><li>Home</li></a>
    [#if logged]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href=""><li>Planning</li></a>
      [/#if]
      [#if currentUser.isCP() || currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href=""><li>Reporting</li></a>
      [/#if]
      [#if currentUser.isTL() || currentUser.isRPL() || currentUser.isAdmin()]
        <a href=""><li>Summaries</li></a>
      [/#if]
      [#if currentUser.isAdmin()]
        <a href=""><li>Admin Area</li></a>
      [/#if]
    [/#if]
  </ul>
</nav>