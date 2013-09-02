[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article>
  <div class="content">
    [#if logged]
      <h1>[@s.text name="home.home.title" /]</h1>
    [#else]
      <h1>[@s.text name="home.login.title" /]</h1>
    [/#if]
    
    [#if logged]
      [#-- Home introduction  --]
      <p> [@s.text name="home.home.introduction" /] </p>
      
      <div id="loginFormContainer">
        <p class="alreadyLogged">[@s.text name="home.login.alreadyLogged" /]</p>
        <span class="alreadyLoggedEmail">${currentUser.email}</span>
    	</div>
      
    [#else]
      [#-- Login introduction  --]
      <p>
        [@s.text name="home.login.introduction" /]
      </p>
      <div id="loginFormContainer">  
        [@s.form method="POST" action="login" cssClass="loginForm"]
          [@s.fielderror cssClass="fieldError" fieldName="loginMesage"/]    	
          [@customForm.input name="user.email" i18nkey="home.login.email" required=true /]
          [@customForm.input name="user.password" i18nkey="home.login.password" required=true type="password" /]
          [@s.submit key="home.login.button" name="login" /]    	
        [/@s.form]
    	</div>
    [/#if]
  	
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer-logos.ftl"]