package controlador;

import java.nio.file.Paths;
import java.io.File;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import modelo.Acudiente;
import modelo.AcudienteDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Paciente;
import modelo.PacienteDAO;
import modelo.Notificacion;
import modelo.NotificacionDAO;
import java.io.PrintWriter;
import modelo.Profesional;
import modelo.ProfesionalDAO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10)   // 10MB

public class Controlador extends HttpServlet {
    
    Profesional em = new Profesional();
    ProfesionalDAO edao = new ProfesionalDAO();

    Usuario usuario = new Usuario();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    Paciente pr = new Paciente();
    PacienteDAO pdao = new PacienteDAO();

    Acudiente cl = new Acudiente();
    AcudienteDAO cdao = new AcudienteDAO();

    Notificacion v = new Notificacion();
    List<Notificacion> lista = new ArrayList<>();
    int item, cod, cant;
    String descripcion, numeroserie;
    double precio, subtotal, total;
    NotificacionDAO vdao = new NotificacionDAO();
    

    Acudiente c = null;

    int ide;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String menu = request.getParameter("menu");
        String rutaRaiz = request.getServletContext().getRealPath("/");

        if ("BuscarDatosAcudiente".equals(accion)) {
            String doc = request.getParameter("documento");
            Acudiente encontrado = cdao.buscarPorDocumento(doc);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            if (encontrado != null) {
                String nombreCompleto = encontrado.getNombres() + " " + encontrado.getApellidos();
                out.print("{\"nombre\": \"" + nombreCompleto + "\", \"telefono\": \"" + encontrado.getTelefono() + "\"}");
            } else {
                out.print("{\"nombre\": \"\", \"telefono\": \"\"}");
            }
            out.flush();
            return;
        }

        if (menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }

        if (menu.equals("Usuario")) {
            switch (accion) {
                case "Listar":
                    List<Usuario> listaUsuarios = usuarioDAO.listar();
                    request.setAttribute("usuarios", listaUsuarios);
                    break;

                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String rol = request.getParameter("txtRol");
                    String correo = request.getParameter("txtCorreo");
                    String est = request.getParameter("txtEstado").equals("Activo") ? "1" : "2";
                    String user = request.getParameter("txtUsuario");
                    String pass = asegurarClave(request.getParameter("txtPass"));

                    usuario.setDni(dni);
                    usuario.setNom(nom);
                    usuario.setRol(rol);
                    usuario.setCorreo(correo);
                    usuario.setEstado(est);
                    usuario.setUser(user);
                    usuario.setPass(pass);

                    usuarioDAO.agregar(usuario);
                    request.getRequestDispatcher("Controlador?menu=Usuario&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Usuario u = usuarioDAO.listarId(ide);
                    request.setAttribute("usuario", u);
                    request.getRequestDispatcher("Controlador?menu=Usuario&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String dni2 = request.getParameter("txtDni");
                    String nom2 = request.getParameter("txtNombres");
                    String rol2 = request.getParameter("txtRol");
                    String correo2 = request.getParameter("txtCorreo");
                    String est2 = request.getParameter("txtEstado").equals("Activo") ? "1" : "2";
                    String user2 = request.getParameter("txtUsuario");
                    String passRaw = request.getParameter("txtPass");

                    usuario.setDni(dni2);
                    usuario.setNom(nom2);
                    usuario.setRol(rol2);
                    usuario.setCorreo(correo2);
                    usuario.setEstado(est2);
                    usuario.setUser(user2);
                    usuario.setId(ide);

                    // Solo actualizar la contraseña si fue proporcionada
                    if (passRaw != null && !passRaw.trim().isEmpty()) {
                        usuario.setPass(asegurarClave(passRaw));
                    } else {
                        // Obtener la contraseña actual desde la base de datos
                        Usuario existente = usuarioDAO.listarId(ide);
                        usuario.setPass(existente.getPass());
                    }

                    usuarioDAO.actualizar(usuario);
                    request.getRequestDispatcher("Controlador?menu=Usuario&accion=Listar").forward(request, response);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    usuarioDAO.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Usuario&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }

            request.getRequestDispatcher("Usuario.jsp").forward(request, response);
        }

        if (menu.equals("Acudiente")) {
            switch (accion) {
                case "Listar":
                    List<Acudiente> listaClientes = cdao.listar();
                    request.setAttribute("clientes", listaClientes);
                    break;

                case "Agregar":
                    Acudiente nuevo = new Acudiente();
                    nuevo.setNombres(request.getParameter("txtNombres"));
                    nuevo.setApellidos(request.getParameter("txtApellidos"));
                    nuevo.setDocumento(request.getParameter("txtDocumento"));
                    nuevo.setTelefono(request.getParameter("txtTelefono"));
                    nuevo.setCorreo(request.getParameter("txtCorreo"));

                    cdao.agregar(nuevo);
                    request.getRequestDispatcher("Controlador?menu=Acudiente&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Acudiente acudienteEdit = cdao.listarId(ide);
                    request.setAttribute("cliente", acudienteEdit);
                    request.getRequestDispatcher("Controlador?menu=Acudiente&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    Acudiente actualizado = new Acudiente();
                    actualizado.setId(ide);
                    actualizado.setNombres(request.getParameter("txtNombres"));
                    actualizado.setApellidos(request.getParameter("txtApellidos"));
                    actualizado.setDocumento(request.getParameter("txtDocumento"));
                    actualizado.setTelefono(request.getParameter("txtTelefono"));
                    actualizado.setCorreo(request.getParameter("txtCorreo"));

                    cdao.actualizar(actualizado);
                    request.getRequestDispatcher("Controlador?menu=Acudiente&accion=Listar").forward(request, response);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    cdao.eliminar(ide);
                    request.getRequestDispatcher("Controlador?menu=Acudiente&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Acudiente.jsp").forward(request, response);
        }

        if (menu.equals("Paciente")) {
            switch (accion) {
                case "Listar":
                    List<Paciente> listaPacientes = pdao.listar();
                    request.setAttribute("pacientes", listaPacientes);
                    break;

                case "Agregar":
                    pr = new Paciente();
                    pr.setNombres(request.getParameter("txtNombres"));
                    pr.setApellidos(request.getParameter("txtApellidos"));
                    pr.setDiagnostico(request.getParameter("txtDiagnostico"));
                    pr.setNumeroDocumento(request.getParameter("txtNumeroDocumento"));
                    pr.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
                    pr.setDireccion(request.getParameter("txtDireccion"));
                    pr.setTelefono(request.getParameter("txtTelefono"));
                    pr.setCorreo(request.getParameter("txtCorreo"));
                    pr.setEstado(request.getParameter("txtEstado"));

                    Part archivoAgregar = request.getPart("txtHistorial");
                    String numeroDocAgregar = request.getParameter("txtNumeroDocumento");

                    String nombreOriginalAgregar = Paths.get(archivoAgregar.getSubmittedFileName()).getFileName().toString();
                    String extensionAgregar = "";
                    int indexAgregar = nombreOriginalAgregar.lastIndexOf(".");
                    if (indexAgregar > 0) {
                        extensionAgregar = nombreOriginalAgregar.substring(indexAgregar);
                    }

                    String nombreArchivoAgregar = numeroDocAgregar + extensionAgregar;

                    // 🛠 Ajuste para evitar que se guarde en /build/web (carpeta temporal)
                    String rutaDefinitiva = rutaRaiz.replace("build\\web\\", "web\\") + "archivos\\" + nombreArchivoAgregar;

                    // Crear la carpeta si no existe
                    File carpetaArchivos = new File(rutaRaiz.replace("build\\web\\", "web\\") + "archivos");
                    if (!carpetaArchivos.exists()) {
                        carpetaArchivos.mkdirs();
                    }

                    archivoAgregar.write(rutaDefinitiva);
                    pr.setHistorial("archivos/" + nombreArchivoAgregar);

                    pdao.agregar(pr);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Paciente paciente = pdao.listarId(ide);
                    request.setAttribute("paciente", paciente);
                    break;

                case "Actualizar":
                    pr.setId(ide);
                    pr.setNombres(request.getParameter("txtNombres"));
                    pr.setApellidos(request.getParameter("txtApellidos"));
                    pr.setDiagnostico(request.getParameter("txtDiagnostico"));
                    pr.setNumeroDocumento(request.getParameter("txtNumeroDocumento"));
                    pr.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
                    pr.setDireccion(request.getParameter("txtDireccion"));
                    pr.setTelefono(request.getParameter("txtTelefono"));
                    pr.setCorreo(request.getParameter("txtCorreo"));
                    pr.setEstado(request.getParameter("txtEstado"));

                    Part archivoActualizar = request.getPart("txtHistorial");
                    String nombreOriginalActualizar = Paths.get(archivoActualizar.getSubmittedFileName()).getFileName().toString();
                    String numeroDocActualizar = request.getParameter("txtNumeroDocumento");

                    if (nombreOriginalActualizar != null && !nombreOriginalActualizar.isEmpty()) {
                        String extensionActualizar = "";
                        int indexActualizar = nombreOriginalActualizar.lastIndexOf(".");
                        if (indexActualizar > 0) {
                            extensionActualizar = nombreOriginalActualizar.substring(indexActualizar);
                        }

                        String nombreArchivoActualizar = numeroDocActualizar + extensionActualizar;

                        // 🛠 Aseguramos ruta persistente fuera de /build/web
                        String rutaFinal = rutaRaiz.replace("build\\web\\", "web\\") + "archivos\\" + nombreArchivoActualizar;
                        File carpetaArchivosActualizar = new File(rutaRaiz.replace("build\\web\\", "web\\") + "archivos");

                        // Crear carpeta si no existe
                        if (!carpetaArchivosActualizar.exists()) {
                            carpetaArchivosActualizar.mkdirs();
                        }

                        archivoActualizar.write(rutaFinal);
                        pr.setHistorial("archivos/" + nombreArchivoActualizar);
                    } else {
                        Paciente anterior = pdao.listarId(ide);
                        pr.setHistorial(anterior.getHistorial());
                    }

                    pdao.actualizar(pr);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    pdao.eliminar(ide);
                    break;

                case "AsignarAcudiente":
                    int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
                    String documentoAcudiente = request.getParameter("documento");
                    String parentesco = request.getParameter("parentesco");
                    String telefonoContacto = request.getParameter("telefonoContacto");

                    Acudiente acudienteAsignado = cdao.buscarPorDocumento(documentoAcudiente);

                    if (acudienteAsignado != null) {
                        pdao.asignarAcudiente(idPaciente, acudienteAsignado.getId(), parentesco, telefonoContacto);
                        request.getSession().setAttribute("mensajePaciente", "✅ Acudiente asignado correctamente.");
                    } else {
                        request.getSession().setAttribute("errorPaciente", "❌ No se encontró un acudiente con ese número de documento.");
                    }

                    response.sendRedirect("Controlador?menu=Paciente&accion=Listar");
                    return;

                case "FormAsignar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Paciente pacienteForm = pdao.listarId(ide);
                    request.setAttribute("paciente", pacienteForm);
                    request.getRequestDispatcher("AsignarAcudiente.jsp").forward(request, response);
                    return;

                case "EliminarAcudiente":
                    int idEliminar = Integer.parseInt(request.getParameter("idPaciente"));
                    pdao.eliminarAsignacionAcudiente(idEliminar);
                    request.getSession().setAttribute("mensajePaciente", "✅ Acudiente eliminado correctamente.");
                    response.sendRedirect("Controlador?menu=Paciente&accion=Listar");
                    return;
            }

            List<Paciente> listaPacientes = pdao.listar();
            request.setAttribute("pacientes", listaPacientes);
            request.getRequestDispatcher("Paciente.jsp").forward(request, response);
        }

        if (menu.equals("VisionMision") && accion.equals("Ver")) {
            request.getRequestDispatcher("VisionMision.jsp").forward(request, response);
        }

        if (menu.equals("Equipo de trabajo") && accion.equals("Ver")) {
            request.getRequestDispatcher("EquipoTrabajo.jsp").forward(request, response);
        }

        if (menu.equals("Trayectoria") && accion.equals("Ver")) {
            request.getRequestDispatcher("Trayectoria.jsp").forward(request, response);
        }

        if (menu.equals("Contacto") && accion.equals("Ver")) {
            request.getRequestDispatcher("Contacto.jsp").forward(request, response);
        }
    }

    public String asegurarClave(String textoClaro) {
        String claveSha = null;

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(textoClaro.getBytes());
            claveSha = Base64.getEncoder().encodeToString(sha256.digest());
            System.out.println("Clave sha es: " + claveSha);
            System.out.println("Longitud:" + claveSha.length());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error en instanciar sha256 " + ex.getMessage());
        }

        return claveSha;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
