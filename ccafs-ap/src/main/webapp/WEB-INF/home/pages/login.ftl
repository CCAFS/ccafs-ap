[#ftl]
[#assign title = "Login to CCAFS Activity Planning" /]
[#assign jsIncludes = ["jquery"] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
<article>
  <div class="content">
    <h1>The CCAFS Planning Website</h1>
    
    <p>
      This site is for the internal use of CCAFS themes, centers  and regions for the planning and subsequent reporting of yearly research activities. The planning phase is open from 01 Jan to 01 May of every year and the reporting phase is open from 01 May to 31 Dec of every year.  To begin filling out activity forms or to return to a form that has already been begun, please log in below with the username and password  provided by the administrator.
    </p>
    
  	<h4>Enter your Email:</h4>
  	[@s.form action="Login.action"]
  		[@s.textfield name="email" label="email" /]
  		[@s.password name="password" label="password" /]
  		[@s.submit value="Login" /]
  	[/@s.form]
  	
  	<p>
  	  Completed activity forms from current and past years are stored on this webpage for reference and planning purposes. To see the complete list of activities by year, theme or center follow the link below.
  	</p>
  	
  	[#if email??]
  		<h4>Your Email is: ${email}</h4>
  	[/#if]
  	
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]