package ejercicio_sesiones;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/tabla")
public class Tabla extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw=resp.getWriter();
		String categoria=req.getParameter("noticias");
		HttpSession sesion=req.getSession();
		String user=(String) sesion.getAttribute("usuario");
		//cuando cierre sesion pondre user a null
		
		//pw.println("sesion del usuario: "+user);
		String usuarioSesion=(String) sesion.getAttribute("usuario");

		if(usuarioSesion==null) {
			throw new ServletException("No has iniciado sesión");
		}
		Connection conexion=null;
		ResultSet rs=null;
		
		
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("	<head>");
		pw.println("		<title>Noticias de: "+categoria+"</title>");
		pw.println("		<meta charset='utf-8'/>");
		pw.println("	</head>");
		pw.println("	<body>");
		pw.println("		<h1>Noticias de: "+categoria+"</h1>");
		pw.println("		<table>");
		pw.println("			<thead>");
		pw.println("				<tr>");
		pw.println("					<th><Identificador/th>");
		pw.println("					<th>Título</th>");
		pw.println("					<th>Texto</th>");
		pw.println("					<th><Categoria/th>");
		pw.println("					<th>Fecha</th>");
		pw.println("				</tr>");
		pw.println("			</thead>");
		pw.println("			<tbody>");
		
		try {
			conexion=DriverManager.getConnection("jdbc:mysql://localhost/inmobiliaria","root","practicas");
			Statement st= conexion.createStatement();
			String sentencia;
			if(categoria.contentEquals("*")) {
				sentencia="select * from noticias";
			}else {
				sentencia="select * from noticias where categoria = '"+categoria+"'";
			}
			rs=st.executeQuery(sentencia);
			while(rs.next()) {
				String id=rs.getString("id");
				String titulo=rs.getString("titulo");
				String texto=rs.getString("texto");
				String cat=rs.getString("categoria");
				String fecha=rs.getString("fecha");
				pw.println("				<tr>");
				pw.println("					<td>"+id+"</td>");
				pw.println("					<td>"+titulo+"</td>");
				pw.println("					<td>"+texto+"</td>");
				pw.println("					<td>"+cat+"</td>");
				pw.println("					<td>"+fecha+"</td>");
				pw.println("				</tr>");
			
			}
		} catch (SQLException e) {
			throw new ServletException("Error con la base de datos: "+e);
		}finally {
			
				try {
					if(rs!=null)
						rs.close();
				} catch (SQLException e) {
					throw new ServletException("Error al cerrar resultset: "+e);
				}
		}
		
		
		pw.println("			</tbody>");
		pw.println("		</table>");
		pw.println("		<p>Desea hacer <a href='consulta'>otra consulta</a></p>");
		pw.println("		<p>Volve al  <a href='gestion.html'>menú de consultas</a></p>");
		pw.println("		<form action='desconexion' method='post'>");
		pw.println("			<input type='submit' name='desconectar' value='Desconexion'>");
		pw.println("		</form>");
		pw.println("	</body>");
		pw.println("</html>");
	}
}
