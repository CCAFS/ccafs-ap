[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/previousVersionMessage.ftl" /]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article>
  <div class="content">
    <h1>[@s.text name="home.login.title" /]</h1>
    
    [#-- Login introduction  --]
    <p>
      [@s.text name="home.login.introduction" /]
    </p>
    <div id="loginFormContainer">  
      [@s.form method="POST" action="login" cssClass="loginForm pure-form"]
        [@s.fielderror cssClass="fieldError" fieldName="loginMesage"/]      
        [@customForm.input name="user.email" i18nkey="home.login.email" required=true /]
        [@customForm.input name="user.password" i18nkey="home.login.password" required=true type="password" /]
        [@s.submit key="home.login.button" name="login" /]      
      [/@s.form]
    </div>
    
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer-logos.ftl"]