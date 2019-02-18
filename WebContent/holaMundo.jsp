<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hola Mundo</title>
</head>
<body>
	<h1>
		<%! Calendar fecha=new GregorianCalendar();
			int anio=fecha.get(Calendar.YEAR);
			int mes=fecha.get(Calendar.MONTH);
			int dia=fecha.get(Calendar.DAY_OF_MONTH);
			String escribir=dia+"/"+mes+"/"+anio;
		%>
		Hola mundo
		<%-- <% 
			Calendar fecha=new GregorianCalendar();
			int anio=fecha.get(Calendar.YEAR);
			int mes=fecha.get(Calendar.MONTH);
			mes=mes+1;
			if(mes==13)
				mes=1;
			int dia=fecha.get(Calendar.DAY_OF_MONTH);
			out.println(dia+"/"+mes+"/"+anio);
		%> --%>
	
		<%--<%= new java.util.Date()%> 
		--%>
		<%= escribir %>
	</h1>
</body>
</html>