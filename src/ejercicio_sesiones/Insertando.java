package ejercicio_sesiones;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/insertando")
public class Insertando extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sesion=req.getSession();
		String user=(String) sesion.getAttribute("usuario");
		//cuando cierre sesion pondre user a null
		
		//pw.println("sesion del usuario: "+user);
		String usuarioSesion=(String) sesion.getAttribute("usuario");

		if(usuarioSesion==null) {
			throw new ServletException("No has iniciado sesión");
		}
		Connection conexion=null;
		int rs;
		ResultSet rs1=null;
		
		PrintWriter pw=resp.getWriter();
		
		//cojo todos los parametros del formulario
		String titulo=req.getParameter("titulo");
		String texto=req.getParameter("texto");
		String cat=req.getParameter("categoria");
		//String fecha=req.getParameter("fecha");//llega bien puesta desde el calendario
		//coger la fecha
		Calendar fecha=new GregorianCalendar();
		int anio=fecha.get(Calendar.YEAR);
		int mes=fecha.get(Calendar.MONTH);
			
		int dia=fecha.get(Calendar.DAY_OF_MONTH);
		String actual=""+anio;
		if(mes<10)
			actual+="-0"+mes;
		else
			actual+="-"+mes;
		if(dia<10)
			actual+="-0"+dia;
		else
			actual+="-"+dia;
			
		//pw.println(titulo+" - "+texto+" - "+cat+" - "+fecha);
		//inserto y muestro  los datos de la noticia que inserte
		
		try {
			conexion=DriverManager.getConnection("jdbc:mysql://localhost/inmobiliaria","root","practicas");
			Statement st= conexion.createStatement();
			rs=st.executeUpdate("insert into noticias(titulo,texto,categoria,fecha) values ('"+titulo+"','"+texto+"','"+cat+"','"+actual+"')");
		
		
	
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("	<head>");
			pw.println("		<title>Noticia insertada</title>");
			pw.println("		<meta charset='utf-8'/>");
			pw.println("	</head>");
			pw.println("	<body>");
			pw.println("		<h1>Noticia Insertada</h1>");
			//cogemos la útlima que hay insertada:
			
			rs1=st.executeQuery("select * from noticias order by id desc limit 1");
			while(rs1.next()) {
				pw.println("		<p><span>Identificador: </span>"+rs1.getInt("id")+"</p>");
				pw.println("		<p><span>Título: </span>"+rs1.getString("titulo")+"</p>");
				pw.println("		<p><span>Texto: </span>"+rs1.getString("texto")+"</p>");
				pw.println("		<p><span>Categoria: </span>"+rs1.getString("categoria")+"</p>");
				pw.println("		<p><span>Fecha: </span>"+rs1.getString("fecha")+"</p>");
			
			}
			pw.println("		<p>Insertar <a href='insertar.html'>otra noticia</a></p>");
			pw.println("		<p>Volver al  <a href='gestion.html'>menú de consultas</a></p>");
			pw.println("		<form action='desconexion' method='post'>");
			pw.println("			<input type='submit' name='desconectar' value='Desconexion'>");
			pw.println("		</form>");
			pw.println("	</body>");
			pw.println("</html>");
		} catch (SQLException e) {
			throw new ServletException("Error al conectar con la base de datos: "+e);
		}
		
	}
}
