
[#ftl]
[#include "/WEB-INF/global/pages/head.ftl"]
<body>
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
      <div id="autoSavingMessages">
        <p id="saving" style="display:none;" >
          <img src="${baseUrl}/images/global/saving.gif" alt="Saving information" />
          [@s.text name="saving.saving" /]
        </p>
        <p id="saved" style="display:none;" >[@s.text name="saving.saved" /]</p>
        <p id="problemSaving" style="display:none;" >[@s.text name="saving.problem" /] </p>
      </div>
    </header>
[/#if]