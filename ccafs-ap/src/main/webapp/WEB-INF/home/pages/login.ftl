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
  	<form class="loginForm" action="Login.do" method="POST">
      [@form.input name="user.email" i18nkey="home.login.email" required=true /]
  		[@form.input name="user.password" i18nkey="home.login.password" required=true type="password" /]
  		[@s.submit key="home.login.button" name="submit" /]
  	</form>
  	</div>
  	
  	<p>
      [@s.text name="home.login.followlink" /]
  	</p>
  	
  	[#if email??]
  		<h4>Your Email is: ${email}</h4>
  	[/#if]
  	
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]