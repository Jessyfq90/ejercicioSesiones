package ejercicio_sesiones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class Errores extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg=(String) req.getAttribute("javax.servlet.error.message");
		resp.setContentType("text/html");
		PrintWriter pw=null;
		pw=resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("	<head>");
		pw.println("		<title>Error!!</title>");
		pw.println("	</head>");
		pw.println("	<body>");
		pw.println("		<h1>HA sucedido un error</h1>");
		pw.println("		<p>"+msg+"</p>");
		pw.println("		<p>Vuelve a la página de <a href='index.html'>iniciar sesión</a></p>");
		pw.println("	</body>");
		pw.println("</html>");
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg=(String) req.getAttribute("javax.servlet.error.message");
		PrintWriter pw=null;
		pw=resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("	<head>");
		pw.println("		<title>Error!!</title>");
		pw.println("	</head>");
		pw.println("	<body>");
		pw.println("		<h1>HA sucedido un error</h1>");
		pw.println("		<p>"+msg+"</p>");
		pw.println("		<p>Vuelve a la página de <a href='index.html'>iniciar sesión</a></p>");
		pw.println("	</body>");
		pw.println("</html>");
	}
}
