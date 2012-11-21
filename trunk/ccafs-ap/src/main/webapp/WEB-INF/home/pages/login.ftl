[#ftl]
[#assign title = "Login to CCAFS Activity Planning" /]
[#assign jsIncludes = ["jquery"] /]
[#assign customCSS = ["${baseUrl}/css/home/login.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

[#import "/WEB-INF/global/macros/forms.ftl" as form /]
<article>
  <div class="content">
    <h1>[@s.text name="home.login.title" /]</h1>
    
    <p>
      [@s.text name="home.login.introduction" /]
    </p>
    <div id="loginFormContainer">
    [#if logged]
      <p class="alreadyLogged">You are already logged in as</p>
      <span class="alreadyLoggedEmail">${currentUser.email}</span>
    [#else]
      [@s.form method="POST" action="login.do" cssClass="loginForm"]
    	<!-- form class="loginForm" action="Login.do" method="POST" -->
        [@form.input name="user.email" i18nkey="home.login.email" required=true /]
    		[@form.input name="user.password" i18nkey="home.login.password" required=true type="password" /]
    		[@s.submit key="home.login.button" name="login" /]
    	<!-- /form -->
    	[/@s.form]
    [/#if]
  	</div>
  	
  	<p>
      [@s.text name="home.login.followlink" /]
  	</p>
  	
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]