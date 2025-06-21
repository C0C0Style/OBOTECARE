package controlador;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Profesional;
import modelo.ProfesionalDAO;

public class Validar extends HttpServlet {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    ProfesionalDAO profesionalDAO = new ProfesionalDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        switch (accion.toLowerCase()) {
            case "ingreso":
                manejarIngreso(request, response);
                break;
            case "salida":
                manejarSalida(request, response);
                break;
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
        }
    }

    private void manejarIngreso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("txtuser");
        String pass = request.getParameter("txtpass");
        String rol = request.getParameter("rol"); // Debes tener este campo en el formulario

        HttpSession sesion = request.getSession();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if ("profesional".equalsIgnoreCase(rol)) {
            Profesional profesional = profesionalDAO.validar(user, pass);
            if (profesional.getUser() != null) {
                sesion.setAttribute("user", profesional);
                request.getRequestDispatcher("Controlador?menu=Profesional&accion=Listar").forward(request, response);
            } else {
                request.setAttribute("mensaje", "❌ Profesional o contraseña incorrectos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            Usuario usuario = usuarioDAO.validar(user, pass);
            if (usuario.getUser() != null) {
                sesion.setAttribute("user", usuario);
                request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
            } else {
                request.setAttribute("mensaje", "❌ Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
    }

    private void manejarSalida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private String asegurarClave(String textoClaro) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(textoClaro.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            System.out.println("Error al encriptar contraseña: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet que maneja la autenticación de usuarios.";
    }
}
