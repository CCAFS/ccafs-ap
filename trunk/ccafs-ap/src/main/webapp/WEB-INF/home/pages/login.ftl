[#ftl]
[#assign title = "Login to CCAFS Activity Planning" /]
[#assign jsIncludes = ["jquery"] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
<article>
  <div class="content">
    <h1>[@s.text name="home.login.title" /]</h1>
    
    <p>
      [@s.text name="home.login.introduction" /]
    </p>
    
  	<form class="loginForm" action="Login.action">
  		[@s.textfield name="email" label="email" /]
  		[@s.password name="password" label="password" /]
  		[@s.submit value="Login" /]
  	</form>
  	
  	<p>
      [@s.text name="home.login.followlink" /]
  	</p>
  	
  	[#if email??]
  		<h4>Your Email is: ${email}</h4>
  	[/#if]
  	
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]