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
import util.CorreoUtil;
import java.util.Date;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import modelo.Profesional;
import modelo.ProfesionalDAO;
import modelo.Recordatorio;
import modelo.RecordatorioDAO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10)   // 10MB

public class Controlador extends HttpServlet {
    
    
    Recordatorio rec = new Recordatorio();
    RecordatorioDAO recao = new RecordatorioDAO();
    
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
    int idrec;

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
        if (menu.equals("Profesional")) {
    switch (accion) {
        case "Listar":
            List<Profesional> listaEmpleados = edao.listar();
            System.out.println("Cantidad de profesionales: " + listaEmpleados.size());
            request.setAttribute("profesionales", listaEmpleados);
            request.getRequestDispatcher("Profesional.jsp").forward(request, response);
            break;

        case "Agregar":
            String dni = request.getParameter("txtDni");
            String nom = request.getParameter("txtNombres");
            String tel = request.getParameter("txtTel");
            String est = request.getParameter("txtEstado"); // Debe ser "1" o "2"
            String user = request.getParameter("txtUsuario");
            String pass = asegurarClave(request.getParameter("txtPass")); // Aplicar hash

            // Validación básica
            if (dni == null || dni.isEmpty() || nom == null || nom.isEmpty() || 
                est == null || user == null || user.isEmpty() || pass == null) {
                request.setAttribute("error", "Faltan campos obligatorios");
                request.getRequestDispatcher("Profesional.jsp").forward(request, response);
                return;
            }

            em.setDni(dni);
            em.setNom(nom);
            em.setTel(tel);
            em.setEstado(est);
            em.setUser(user);
            em.setPass(pass);

            int resultado = edao.agregar(em);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Profesional agregado correctamente");
            } else {
                request.setAttribute("error", "No se pudo agregar el profesional");
            }

            request.getRequestDispatcher("Controlador?menu=Profesional&accion=Listar").forward(request, response);
            break;

        case "Editar":
            ide = Integer.parseInt(request.getParameter("id"));
            Profesional e = edao.listarId(ide);
            request.setAttribute("empleado", e);
            request.getRequestDispatcher("Controlador?menu=Profesional&accion=Listar").forward(request, response);
            break;
            
        case "Pacientes": 
                           
                            int idProfesional = Integer.parseInt(request.getParameter("id"));
                            
                            // Obtener los pacientes ASIGNADOS a este profesional
                            List<Paciente> listaPacientesAsignados = pdao.listarPacientesPorProfesional(idProfesional); 
                            
                            // También podríamos querer pasar el objeto profesional a la JSP
                            // para mostrar su nombre en el encabezado de la lista de pacientes.
                            Profesional profActual = edao.listarId(idProfesional); // Asumiendo edao es ProfesionalDAO
                            
                            request.setAttribute("pacientes", listaPacientesAsignados);
                            request.setAttribute("profesionalActual", profActual); // Para mostrar "Pacientes de [Nombre Profesional]"

                            request.getRequestDispatcher("Pacientes.jsp").forward(request, response); // <-- ¡Nueva JSP!
                            return; 
        

        case "Actualizar":
            String dni2 = request.getParameter("txtDni");
            String nom2 = request.getParameter("txtNombres");
            String tel2 = request.getParameter("txtTel");

            String est2;
            if (request.getParameter("txtEstado").equals("Activo")) {
                est2 = "1";
            } else {
                est2 = "2";
            }

            String user2 = request.getParameter("txtUsuario");
            String nuevaPass = request.getParameter("txtPass");

            em.setDni(dni2);
            em.setNom(nom2);
            em.setTel(tel2);
            em.setEstado(est2);
            em.setUser(user2);
            em.setId(ide);

            // Solo actualiza la contraseña si se ha ingresado una nueva
            if (nuevaPass != null && !nuevaPass.isEmpty()) {
                em.setPass(asegurarClave(nuevaPass));
            } else {
                em.setPass(null); // Para que el DAO sepa que no debe actualizarla
            }

            edao.actualizar(em);

            request.getRequestDispatcher("Controlador?menu=Profesional&accion=Listar").forward(request, response);
            break;

        case "Borrar":
            ide = Integer.parseInt(request.getParameter("id"));
            edao.delete(ide);
            request.getRequestDispatcher("Controlador?menu=Profesional&accion=Listar").forward(request, response);
            break;

        default:
            throw new AssertionError();
    }
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

                case "VerPacientes":
                    int idAcudiente = Integer.parseInt(request.getParameter("id"));
                    List<Paciente> pacientesAsignados = pdao.listarPorAcudiente(idAcudiente);
                    Acudiente acudiente = cdao.listarId(idAcudiente);
                    request.setAttribute("acudiente", acudiente);
                    request.setAttribute("pacientesAsignados", pacientesAsignados);
                    request.getRequestDispatcher("PacientesDeAcudiente.jsp").forward(request, response);
                    return;
                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Acudiente.jsp").forward(request, response);
        }

        if (menu.equals("Notificacion") && accion.equals("Programar")) {
            int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
            String mensaje = request.getParameter("mensaje");
            String fechaHora = request.getParameter("fechaHora"); // formato: yyyy-MM-ddTHH:mm

            String correoDestino = pdao.listarId(idPaciente).getCorreo();
            Date fechaEnvio = java.sql.Timestamp.valueOf(fechaHora.replace("T", " ") + ":00");

            // Agendar envío por correo (antes de redireccionar)
            new java.util.Timer().schedule(new java.util.TimerTask() {
                public void run() {
                    CorreoUtil.enviarCorreo(correoDestino, "Recordatorio", mensaje);
                }
            }, fechaEnvio);

            // Redirige después de agendar
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "✅ Notificación programada.");

            // ❗ Importante: FINALIZA aquí el bloque con return
            response.sendRedirect("Controlador?menu=Acudiente&accion=Listar");
            return;
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
                    
                case "FormAsignarProfesional":
                    // 1. Obtener el ID del paciente desde la URL
                    ide = Integer.parseInt(request.getParameter("id"));
                    

                    // 2. Obtener el objeto Paciente completo
                    // Asegúrate de que tu PacienteDAO.listarId(id) retorne el Paciente completo
                    // Incluyendo su idProfesional actual si lo tiene.
                    Paciente pacienteSeleccionado = pdao.listarId(ide); 

                    // 3. Obtener la lista de todos los profesionales disponibles
                    List<Profesional> listaProfesionales = edao.listar(); // Usamos 'edao' para ProfesionalDAO

                    // 4. Establecer los atributos para la página AsignarProfesional.jsp
                    request.setAttribute("pacienteSeleccionado", pacienteSeleccionado);
                    request.setAttribute("profesionales", listaProfesionales);

                    // 5. Redirigir a la página de asignación
                    request.getRequestDispatcher("asignarProfesional.jsp").forward(request, response);
                    return; // Salir del switch interno

                    case "AsignarProfesional":
                    // 1. Obtener los IDs del paciente y profesional del formulario
                    int idPacienteAsignar = Integer.parseInt(request.getParameter("idPaciente"));
                    int idProfesionalAsignar = Integer.parseInt(request.getParameter("idProfesional"));
                    
                    
                    System.out.println("Debug: ID Paciente a asignar: " + idPacienteAsignar);
                    System.out.println("Debug: ID Profesional a asignar: " + idProfesionalAsignar);
                    
                    System.out.println("Debug: ID Paciente a asignar: " + idPacienteAsignar);
                    System.out.println("Debug: ID Profesional a asignar: " + idProfesionalAsignar);

                    int filasAfectadas = pdao.asignarProfesional(idPacienteAsignar, idProfesionalAsignar); // <-- Captura el resultado

                    if (filasAfectadas > 0) {
                        request.getSession().setAttribute("mensajePaciente", "Profesional asignado correctamente.");
                        System.out.println("Debug: Asignación exitosa, filas afectadas: " + filasAfectadas);
                    } else {
                        request.getSession().setAttribute("mensajePaciente", "No se pudo asignar el profesional. Verifique los IDs.");
                        System.out.println("Debug: Asignación fallida, filas afectadas: " + filasAfectadas);
    }

                    // 2. Llamar al método asignarProfesional de PacienteDAO
                    // El método de tu DAO ya maneja si idProfesional es 0 para asignar NULL.
                    pdao.asignarProfesional(idPacienteAsignar, idProfesionalAsignar); 

                    // Establecer mensaje de éxito
                    request.getSession().setAttribute("mensajePaciente", "Profesional asignado correctamente.");

                    // 3. Redirigir de vuelta a la lista de pacientes
                    response.sendRedirect("Controlador?menu=Paciente&accion=Listar");
                    return; // Salir del switch interno

                case "DesvincularProfesional": // <-- NUEVA ACCIÓN para el botón
                        int idPacienteDesvincular = Integer.parseInt(request.getParameter("id"));
                        
                        // *** LLAMADA A TU MÉTODO EXISTENTE ***
                        pdao.eliminarAsignacionProfesional(idPacienteDesvincular);
                        
                        // Puedes añadir un mensaje de confirmación si lo deseas
                        request.getSession().setAttribute("mensajePaciente", "Profesional desvinculado correctamente.");
                        System.out.println("Debug: Profesional desvinculado para Paciente ID: " + idPacienteDesvincular);
                        
                        response.sendRedirect("Controlador?menu=Paciente&accion=Listar");
                        return; // Importante para salir del método   
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

        if (menu.equals("Notificacion") && accion.equals("Programar")) {
            int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
            String mensaje = request.getParameter("mensaje");
            String fechaHora = request.getParameter("fechaHora"); // formato: yyyy-MM-ddTHH:mm

            String correoDestino = pdao.listarId(idPaciente).getCorreo();
            Date fechaEnvio = java.sql.Timestamp.valueOf(fechaHora.replace("T", " ") + ":00");

            // Capturar sesión para redirección inmediata
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "✅ Notificación programada.");
            response.sendRedirect("Controlador?menu=Acudiente&accion=Listar");

            // Agendar envío por correo
            new java.util.Timer().schedule(new java.util.TimerTask() {
                public void run() {
                    CorreoUtil.enviarCorreo(correoDestino, "Recordatorio", mensaje);
                }
            }, fechaEnvio);
        }

     
    
    if (menu.equals("Notificaciones")) {
            // Obtener el usuario logueado de la sesión
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("user"); // Esto es un Usuario, como quieres mantenerlo

            // Variable para almacenar el ID del profesional creador
            int idProfesionalCreador = 0; // Por defecto a 0 o algún valor que indique "no asignado"

            // Si hay un usuario logueado y su rol es "Profesional"
            if (usuarioLogueado != null && "Profesional".equals(usuarioLogueado.getRol())) {
                // *** Aquí es donde necesitamos obtener el ID del Profesional.
                // Opción A: Si el ID del Usuario y el ID del Profesional son el mismo
                idProfesionalCreador = usuarioLogueado.getId();

             
                Profesional profesionalAsociado = edao.listarId(usuarioLogueado.getId()); // Necesitarías este método en ProfesionalDAO
                if (profesionalAsociado != null) {
                    idProfesionalCreador = profesionalAsociado.getId();
                } else {
                    // Si el usuario tiene rol Profesional pero no se encuentra su perfil de Profesional
                    // Esto es una situación excepcional, podrías loggearla o redirigir
                    System.err.println("Advertencia: Usuario con rol 'Profesional' no tiene perfil de Profesional asociado. Usuario ID: " + usuarioLogueado.getId());
                }
            }
            // Si el usuario logueado NO es un profesional o no está logueado, idProfesionalCreador seguirá siendo 0.

            switch (accion) {
                case "Listar": // 3. Poder ver la lista de recordatorios (todos)
                    List<Recordatorio> listaRecordatorios = recao.listar();
                    request.setAttribute("recordatorios", listaRecordatorios);
                    request.getRequestDispatcher("RecordatoriosGeneral.jsp").forward(request, response);
                    break;

                case "ListarPorPaciente": // Listar recordatorios de un paciente en particular
                    int idPacienteListar = Integer.parseInt(request.getParameter("idPaciente"));
                    List<Recordatorio> recordatoriosPaciente = recao.listarPorPaciente(idPacienteListar);
                    Paciente pacienteParaRecordatorios = pdao.listarId(idPacienteListar);

                    request.setAttribute("recordatorios", recordatoriosPaciente);
                    request.setAttribute("pacienteActual", pacienteParaRecordatorios);
                    request.getRequestDispatcher("RecordatoriosPaciente.jsp").forward(request, response);
                    break;

                case "FormAgregar": // Muestra el formulario para crear un nuevo recordatorio (1. Crear)
                    int idPacienteForm = 0;
                    if (request.getParameter("idPaciente") != null && !request.getParameter("idPaciente").isEmpty()) {
                        idPacienteForm = Integer.parseInt(request.getParameter("idPaciente"));
                        Paciente pacienteF = pdao.listarId(idPacienteForm);
                        request.setAttribute("pacienteSeleccionado", pacienteF);
                    }

                    // Pasar el idProfesionalCreador obtenido al request
                    request.setAttribute("idProfesionalCreador", idProfesionalCreador);
                    request.setAttribute("listaPacientes", pdao.listar()); // Necesario para el dropdown en el form
                    request.setAttribute("listaProfesionales", edao.listar()); // Necesario para el dropdown en el form

                    request.getRequestDispatcher("RecordatorioForm.jsp").forward(request, response);
                    break;

                case "Agregar": // Procesa la adición de un nuevo recordatorio (1. Crear)
                    int idPac = Integer.parseInt(request.getParameter("idPaciente"));
                    String fechaHoraStr = request.getParameter("txtFechaHora");
                    String desc = request.getParameter("txtDescripcion");
                    String estado = "Pendiente";
                    // Obtener idProfCreador del parámetro oculto del formulario o del calculado previamente
                    int idProfCreadorForm = 0;
                    if (request.getParameter("idProfesionalCreador") != null && !request.getParameter("idProfesionalCreador").isEmpty()) {
                         idProfCreadorForm = Integer.parseInt(request.getParameter("idProfesionalCreador"));
                    } else {
                        // Si no viene del form (por ejemplo, si el campo oculto no se usó), usar el calculado
                        idProfCreadorForm = idProfesionalCreador;
                    }

                    LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr); // Conversión sin try-catch

                    Recordatorio nuevoRecordatorio = new Recordatorio();
                    nuevoRecordatorio.setIdPaciente(idPac);
                    nuevoRecordatorio.setFechaRecordatorio(fechaHora);
                    nuevoRecordatorio.setDescripcion(desc);
                    nuevoRecordatorio.setEstado(estado);
                    nuevoRecordatorio.setIdProfesionalCreador(idProfCreadorForm); // Usar el ID obtenido/calculado

                    int res = recao.agregar(nuevoRecordatorio);

                    if (res > 0) {
                        request.getSession().setAttribute("mensaje", "Recordatorio agregado correctamente.");
                    } else {
                        request.getSession().setAttribute("error", "No se pudo agregar el recordatorio.");
                    }
                    response.sendRedirect("Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=" + idPac);
                    return; // Importante para detener la ejecución y redirigir

                case "Editar": // Muestra el formulario para editar un recordatorio (2. Editar)
                    idrec = Integer.parseInt(request.getParameter("id")); // idrec se usa aquí
                    Recordatorio recordatorioAEditar = recao.listarId(idrec);
                    request.setAttribute("recordatorio", recordatorioAEditar);

                    request.setAttribute("listaPacientes", pdao.listar());
                    request.setAttribute("listaProfesionales", edao.listar());

                    // Asegurarse de que el ID del profesional creador también esté disponible si se necesita para la edición
                    request.setAttribute("idProfesionalCreador", idProfesionalCreador);

                    request.getRequestDispatcher("RecordatorioForm.jsp").forward(request, response);
                    break;

                case "Actualizar": // Procesa la actualización de un recordatorio (2. Editar)
                    idrec = Integer.parseInt(request.getParameter("idRecordatorio")); // idrec se usa aquí
                    int idPacActualizar = Integer.parseInt(request.getParameter("idPaciente"));
                    String fechaHoraStrActualizar = request.getParameter("txtFechaHora");
                    String descActualizar = request.getParameter("txtDescripcion");
                    String estadoActualizar = request.getParameter("txtEstado");
                    
                    int idProfCreadorActualizar = 0;
                    if (request.getParameter("idProfesionalCreador") != null && !request.getParameter("idProfesionalCreador").isEmpty()) {
                        idProfCreadorActualizar = Integer.parseInt(request.getParameter("idProfesionalCreador"));
                    } else {
                        // Si no viene del form, usar el ID del profesional logueado calculado
                        idProfCreadorActualizar = idProfesionalCreador;
                    }

                    LocalDateTime fechaHoraActualizar = LocalDateTime.parse(fechaHoraStrActualizar); // Conversión sin try-catch

                    Recordatorio recordatorioActualizado = new Recordatorio();
                    recordatorioActualizado.setIdRecordatorio(idrec);
                    recordatorioActualizado.setIdPaciente(idPacActualizar);
                    recordatorioActualizado.setFechaRecordatorio(fechaHoraActualizar);
                    recordatorioActualizado.setDescripcion(descActualizar);
                    recordatorioActualizado.setEstado(estadoActualizar);
                    recordatorioActualizado.setIdProfesionalCreador(idProfCreadorActualizar); // Usar el ID obtenido/calculado

                    int resActualizar = recao.actualizar(recordatorioActualizado);

                    if (resActualizar > 0) {
                        request.getSession().setAttribute("mensaje", "Recordatorio actualizado correctamente.");
                    } else {
                        request.getSession().setAttribute("error", "No se pudo actualizar el recordatorio.");
                    }
                    response.sendRedirect("Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=" + idPacActualizar);
                    return;

                case "Borrar": // Eliminar recordatorios (4. Eliminar)
                    idrec = Integer.parseInt(request.getParameter("id")); // idrec se usa aquí

                    // Obtener el idPaciente antes de borrar para poder redirigir correctamente
                    Recordatorio recordatorioParaBorrar = recao.listarId(idrec);
                    int idPacienteBorrar = (recordatorioParaBorrar != null) ? recordatorioParaBorrar.getIdPaciente() : 0;

                    int resDelete = recao.delete(idrec);

                    if (resDelete > 0) {
                        request.getSession().setAttribute("mensaje", "Recordatorio eliminado correctamente.");
                    } else {
                        request.getSession().setAttribute("error", "No se pudo eliminar el recordatorio.");
                    }

                    if (idPacienteBorrar > 0) {
                        response.sendRedirect("Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=" + idPacienteBorrar);
                    } else {
                        response.sendRedirect("Controlador?menu=Notificaciones&accion=Listar");
                    }
                    return;

                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
            
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
