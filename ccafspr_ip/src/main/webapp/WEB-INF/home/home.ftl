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
    <h1>[@s.text name="home.home.title" /]</h1>

    [#-- Home introduction  --]
    <p> [@s.text name="home.home.introduction" /] </p>
    
    <div id="loginFormContainer">
      <p class="alreadyLogged">[@s.text name="home.login.alreadyLogged" /]</p>
      <span class="alreadyLoggedEmail">${currentUser.email}</span>
    </div>
      
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer-logos.ftl"]