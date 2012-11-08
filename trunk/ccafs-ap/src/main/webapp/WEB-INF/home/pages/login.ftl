[#ftl]
<html>
<head>
<title>Home</title>
</head>
<body>
	<h4>Enter your Name:</h4>
	[@s.form action="Login.action"]
		[@s.textfield name="email" label="Ingrese su correo" /]
		[@s.submit value="Submit" /]
	[/@s.form]
	
	[#if email??]
		<h4>Your Email is: ${email}</h4>
	[/#if]
</body>
</html>