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
@WebServlet("/eliminados")
public class Elimina extends HttpServlet {
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		String [] eliminidos=new String[20];
		int contador=0;
		try {
			conexion=DriverManager.getConnection("jdbc:mysql://localhost/inmobiliaria","root","practicas");
			Statement st= conexion.createStatement();
			
			//cojo los que tienen el eliminar marcado
			String [] aEliminar=req.getParameterValues("borrar[]");
			String sentencia;
			for(int i=0;i<aEliminar.length;i++) {
				//voy a crear un array con los datos de los que voy a borrar para luego mostrarlos
				sentencia="select * from noticias where id='"+aEliminar[i]+"'";
				rs=st.executeQuery(sentencia);
				while(rs.next()) {
					String id=rs.getString("id");
					String titulo=rs.getString("titulo");
					String texto=rs.getString("texto");
					String categoria=rs.getString("categoria");
					String fecha=rs.getString("fecha");
					//creo un string separando las cosas con # y lo guardo
					//al leer hago split para cortar las cadenas
					String guardar=id+"#"+titulo+"#"+texto+"#"+categoria+"#"+fecha;
					pw.println(guardar);
				}
				//pw.println("eliminar id: "+aEliminar[i]);
			}
		}catch(SQLException e) {
			throw new ServletException("Error con la base de datos: "+e);
		}
	}
}
