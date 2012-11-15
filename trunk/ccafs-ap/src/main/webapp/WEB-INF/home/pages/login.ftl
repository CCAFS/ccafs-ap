[#ftl]
[#assign jsIncludes = ["jquery"] /]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


	<h4>Enter your Email:</h4>
	[@s.form action="Login.action"]
		[@s.textfield name="email" label="Ingrese su correo" /]
		[@s.submit value="Submit" /]
	[/@s.form]
	
	[#if email??]
		<h4>Your Email is: ${email}</h4>
	[/#if]
	

[#include "/WEB-INF/global/pages/footer.ftl"]