package controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Profesional;
import modelo.ProfesionalDAO;

public class Validar extends HttpServlet {

    ProfesionalDAO edao = new ProfesionalDAO();
    Profesional em = new Profesional();

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
        em = edao.validar(user, pass);

        System.out.println("Clave ingresada: " + pass);
            System.out.println("Usuario: " + user + " | Contraseña: " + pass);
        if (em.getUser() != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            HttpSession sesion = request.getSession();
            sesion.setAttribute("user", em);

            System.out.println("Sesión creada: " + sesion.getId());

            request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
        } else {
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

    @Override
    public String getServletInfo() {
        return "Servlet que maneja la autenticación de usuarios.";
    }
}
