<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h4>Enter Your Name</h4>
	<s:form action="Ejemplo.action">
		<s:textfield name="name" label="Ingrese su nombre" />
		<s:submit />
	</s:form>
</body>
</html>