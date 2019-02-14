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
@WebServlet("/consulta")
public class consulta extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw=resp.getWriter();
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
		pw.println("		<title>Consulta de noticias</title>");
		pw.println("	</head>");
		pw.println("	<body>");
		pw.println("		<h1>Consultar noticias</h1>");
		pw.println("		<form action='tabla' method='post'>");
		pw.println("			<label for='noticias'>Mostrar noticias de la categoria: </label>");
		pw.println("			<select name='noticias' id='noticias'>");
		pw.println("				<option value=''>Seleccione una categoria</option>");
		pw.println("				<option value='*'>Todas</option>");
		//coger de la base de datos las categorias que hay
		try {
			conexion=DriverManager.getConnection("jdbc:mysql://localhost/inmobiliaria","root","practicas");
			Statement st= conexion.createStatement();
			rs=st.executeQuery("select distinct categoria from noticias");
			while(rs.next()) {
				String tipo=rs.getString("categoria");
				pw.println("				<option>"+tipo+"</option>");
			
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
		
		pw.println("			</select>");
		pw.println("			<input type='submit' name='enviar' value='Mostrar noticias'/>");
		pw.println("		</form>");
		
		pw.println("		<p>Volver al  <a href='gestion.html'>menú de consultas</a></p>");
		pw.println("		<form action='desconexion' method='post'>");
		pw.println("			<input type='submit' name='desconectar' value='Desconexion'>");
		pw.println("		</form>");
		pw.println("	</body>");
		pw.println("</html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
