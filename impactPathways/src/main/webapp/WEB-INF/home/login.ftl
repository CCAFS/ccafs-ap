[#ftl]
[#assign title = "Welcome to CCAFS P&R" /]
[#assign globalLibs = ["jquery", "jreject", "highcharts" ] /]
[#assign customJS = ["${baseUrl}/js/home/login.js" ] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article>
  <div id="loginContainer" class="content">
      [#-- Login introduction  --]
      <h1>[@s.text name="home.login.title" /]</h1>
      <p class="introduction">[@s.text name="home.login.introduction" /]</p>
      <p class="disclaimer">[@s.text name="home.login.disclaimer" /]</p>
    <div class="leftSide">
      <div id="imgPandR">
        <a href="#" onClick="workflowModal()"><img id="imgModal" src="${baseUrl}/images/global/pandrWorkflow.png"/></a>
      </div>
    </div> <!-- End Left Side -->
    <div class="rightSide">
      <div id="loginFormContainer">  
          <div class="loginForm instructions">
            [#-- @s.text name="home.login.message.nonCgiar" / --]
            <p>Thank you for being part of the <strong>exclusive group of testers</strong>; you are in the right place! Please enter your credentials below.</p>
            <p>Should you not be part of the group of testers, please go to the temporary CCAFS P&R by clicking <a href="http://davinci.ciat.cgiar.org/ip">here</a></p>
          </div>
        [@s.form method="POST" action="login" cssClass="loginForm pure-form"]
          [@s.fielderror cssClass="fieldError" fieldName="loginMesage"/]
          [@customForm.input name="user.email" i18nkey="home.login.email" required=true /]
          [@customForm.input name="user.password" i18nkey="home.login.password" required=true type="password" /]
          [@s.submit key="home.login.button" name="login" /]      
        [/@s.form]
      </div><!-- End loginFormContainer -->
    </div><!-- End rightSide -->
    <br>
  </div> <!-- End content -->
</article>
[#-- Show P&R proccess workflow --]
<div id="showPandRWorkflowDialog" style="display:none; height:100%;  width: 100%;" title="[@s.text name="home.dashboard.workflow" /]"> 
  <div sytle="height:100%;  width: 100%;">
    <img id="imgBigModal" src="${baseUrl}/images/global/pandrWorkflow.png"/>
  </div>
</div> 
[#include "/WEB-INF/global/pages/footer-logos.ftl"]