package ejercicio_sesiones;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;


@WebServlet("/login")
public class Login extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//tipo de la respuesta
		resp.setContentType("text/html");
		//cojo la fecha del formulario
		String user=req.getParameter("usuario");
		String clave=req.getParameter("clave");
		PrintWriter pw=null;
	
		if(user.isEmpty() || clave.isEmpty()) {
			throw new ServletException("No has introducido valores en usuario y/o clave");
			
		}else {
			
			Connection conexion=null;
			ResultSet rs=null;
			try {
				//le doy valor al printWriter
				pw=resp.getWriter();
				//creo la conexion
				conexion=DriverManager.getConnection("jdbc:mysql://localhost/inmobiliaria","root","practicas");
				Statement st= conexion.createStatement();
				boolean encontrado=false;
				
				pw.println("<!DOCTYPE html>");
				pw.println("<html>");
				pw.println("	<head>");
				pw.println("		<title>Login</title>");
				pw.println("	</head>");
				pw.println("	<body>");
				pw.println("		<h1>Datos que llegan del formulario</h1>");
				//TEngo que comprobar si user y clave estan en la base de datos.
				rs=st.executeQuery("select * from usuario");
				while(rs.next()) {
					String userActual=rs.getString("usuario");
					String claveActual=rs.getString("clave");
					if(userActual.equals(user) && claveActual.equals(clave)) {
					//Encontre el usuario, se crea aqui la sesion y se redirecciona
						encontrado=true;
						HttpSession sesion=req.getSession();
						//if(sesion.getAttribute("usuario")!=user) { esto lo aria más arriba
						sesion.setAttribute("usuario", user);
						pw.println("	</body>");
						pw.println("</html>");
						resp.sendRedirect("gestion.html");
						//}
					}
				}
				if(!encontrado) {
					pw.println("	</body>");
					pw.println("</html>");
					
					throw new ServletException("El usuario no existe en la base de datos");
				}
			}catch(SQLException e) {
				throw new ServletException(e);
			}finally {
				
					try {
						if(rs!=null)
							rs.close();
					} catch (SQLException e) {
						throw new ServletException("error al cerrar result set clase login");
					}
			}
		}
	}
}
