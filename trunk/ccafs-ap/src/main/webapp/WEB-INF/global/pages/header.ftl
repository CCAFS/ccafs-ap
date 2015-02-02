
[#ftl]
[#include "/WEB-INF/global/pages/head.ftl"]
<body>
[#include "/WEB-INF/global/pages/boardMessage.ftl" /]
[#include "/WEB-INF/global/pages/previousVersionMessage.ftl" /]
[#if !(avoidHeader!false)]
  <div class="container">    
    <header class="clearfix">
      [#if logged]
        <div id="userInfo">
          <span class="email">${currentUser.email}</span>
          <span class="logout"><a href="[@s.url action="logout" namespace="/" /]">[@s.text name="header.logout" /]</a></span>
        </div>
      [/#if]
      <div id="mainLogo"><img src="${baseUrl}/images/global/logo-ap.png" alt="CCAFS Activity Planning Logo" width="500px"></img></div>
      <div id="ccafsLogo"><img src="${baseUrl}/images/global/logo-ccafs.png" alt="CCAFS Logo" width="350px"></div>      
    </header>
[/#if]