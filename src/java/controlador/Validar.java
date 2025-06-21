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

public class Validar extends HttpServlet {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usuario = new Usuario();

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
                if (!response.isCommitted()) {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                break;
        }
    }

    private void manejarIngreso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("txtuser");
        String pass = request.getParameter("txtpass");
        //String passCifrada = asegurarClave(pass);

        usuario = usuarioDAO.validar(user, pass);

        System.out.println("Usuario: " + user + " | Contraseña ingresada: " + pass);

        if (usuario.getUser() != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            HttpSession sesion = request.getSession();
            sesion.setAttribute("user", usuario);

            System.out.println("Sesión creada: " + sesion.getId());
            request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
        } else {
            request.setAttribute("mensaje", "❌ Usuario o contraseña incorrecta.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void manejarSalida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("user");
        sesion.invalidate();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if (!response.isCommitted()) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            System.out.println("Advertencia: La respuesta ya fue comprometida, no se puede reenviar.");
        }
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
