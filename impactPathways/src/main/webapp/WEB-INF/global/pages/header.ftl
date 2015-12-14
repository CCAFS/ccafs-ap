[#ftl]
[#include "/WEB-INF/global/pages/head.ftl"]
<body>
[#include "/WEB-INF/global/pages/boardMessage.ftl" /]
[#if !(avoidHeader!false)]
  <div class="container">  
    <header class="clearfix">
      <div id="mainLogo">Planning & Reporting Platform
      [#if !config.production]<p id="testingMessage"> Test platform </p>[/#if]
      [#if config.closed]<p id="infoMessage"> Closed </p>[/#if]
      </div>
      <div id="ccafsLogo"><img src="${baseUrl}/images/global/logo-ccafs.png" alt="CCAFS Logo" width="300px"></div>
      <div id="autoSavingMessages">
        <p id="saving" style="display:none;" >
          <img src="${baseUrl}/images/global/saving.gif" alt="Saving information" />
          [@s.text name="saving.saving" /]
        </p>
        <p id="saved" style="display:none;" >[@s.text name="saving.saved" /]</p>
        <p id="problemSaving" style="display:none;" >[@s.text name="saving.problem" /] </p>
      </div> 
    </header>
   </div> 
[/#if]