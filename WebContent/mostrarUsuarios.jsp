<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.sql.Connection" import="java.sql.Statement" import="java.sql.ResultSet" import="java.sql.SQLException" import="java.sql.DriverManager"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuarios</title>
</head>
<body>
	<%!
		Connection conexion=null;
		Statement st;
		ResultSet rs=null;
		String sentencia;
		int numEmple;
		String nombre,apellido,genero,fnac;
	%>
	<h1>Mostrar usuarios</h1>
	<table>
		<thead>
			<tr>
				<th>NºEmpleado</th>
				<th>Nombre</th>
				<th>Apellido</th>
				<th>Género</th>
				<th>Fecha nacimiento</th>
			</tr>
		</thead>
		<tbody>
	<%
		try{
			conexion=DriverManager.getConnection("jdbc:mysql://localhost/employees","root","practicas");
			st= conexion.createStatement();
			sentencia="select * from employees limit 5";
			rs=st.executeQuery(sentencia);
			while(rs.next()){
				numEmple=rs.getInt("emp_no");
				nombre=rs.getString("first_name");
				apellido=rs.getString("last_name");
				genero=rs.getString("gender");
				fnac=rs.getString("birth_date");
	%>
			<tr>
				<td><%= numEmple%></td>
				<td><%= nombre%></td>
				<td><%= apellido%></td>
				<td><%= genero%></td>
				<td><%= fnac%></td>
			</tr>
	<% 
			}
		}catch(SQLException e){
			out.println("error: "+e);
		}finally{
			try {
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				out.println("error: "+e);
			}
		}
	%>
		</tbody>
	</table>
</body>
</html>