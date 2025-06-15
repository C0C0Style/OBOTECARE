package controlador;

import java.nio.file.Paths;
import java.io.File;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;
import config.GenerarSerie;
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
import modelo.Profesional;
import modelo.ProfesionalDAO;
import modelo.Paciente;
import modelo.PacienteDAO;
import modelo.Notificacion;
import modelo.NotificacionDAO;
import java.io.PrintWriter;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10)   // 10MB

public class Controlador extends HttpServlet {

    Profesional em = new Profesional();
    ProfesionalDAO edao = new ProfesionalDAO();

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

        if (menu.equals("Empleado")) {
            switch (accion) {
                case "Listar":
                    List<Profesional> listaEmpleados = edao.listar();
                    request.setAttribute("empleados", listaEmpleados);
                    break;

                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est;

                    if (request.getParameter("txtEstado").equals("Activo")) {
                        est = "1";
                    } else {
                        est = "2";
                    }

                    String user = request.getParameter("txtUsuario");
                    String pass = asegurarClave(request.getParameter("txtPass"));

                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);

                    edao.agregar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Profesional e = edao.listarId(ide);
                    request.setAttribute("empleado", e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

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
                    String pass2 = asegurarClave(request.getParameter("txtPass"));

                    em.setDni(dni2);
                    em.setNom(nom2);
                    em.setTel(tel2);
                    em.setEstado(est2);
                    em.setUser(user2);
                    em.setId(ide);

                    edao.actualizar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    edao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Profesional.jsp").forward(request, response);
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
                    pr = new Paciente(); // ← ← IMPORTANTE: limpiar
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
                    String rutaAgregar = getServletContext().getRealPath("/archivos") + File.separator + nombreArchivoAgregar;
                    archivoAgregar.write(rutaAgregar);
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
                        String rutaActualizar = getServletContext().getRealPath("/archivos") + File.separator + nombreArchivoActualizar;
                        archivoActualizar.write(rutaActualizar);
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

            // Siempre después de cada acción, cargar la lista actualizada
            List<Paciente> listaPacientes = pdao.listar();
            request.setAttribute("pacientes", listaPacientes);

            // Redirigir a la vista al final del bloque
            request.getRequestDispatcher("Paciente.jsp").forward(request, response);
        }

        /*if (menu.equals("NuevaVenta")) {
            switch (accion) {
                case "Search":
                    String dni = request.getParameter("codigocliente");
                    cl.setDni(dni);
                    c = cdao.buscar(dni);
                    request.setAttribute("c", c);
                    request.setAttribute("nserie", numeroserie);
                    break;

                case "ProductSearch":
                    int id = Integer.parseInt(request.getParameter("codigoproducto"));
                    pr = pdao.listarId(id);
                    request.setAttribute("c", c);
                    request.setAttribute("producto", pr);
                    request.setAttribute("total", total);
                    request.setAttribute("lista", lista);
                    request.setAttribute("nserie", numeroserie);
                    break;

                case "Agregar":
                    request.setAttribute("nserie", numeroserie);
                    request.setAttribute("c", c);
                    total = 0.0;
                    item = item + 1;
                    cod = pr.getId();
                    descripcion = request.getParameter("nomproducto");
                    precio = Double.parseDouble(request.getParameter("precio"));
                    cant = Integer.parseInt(request.getParameter("cant"));
                    subtotal = precio * cant;
                    v = new Notificacion();
                    v.setItem(item);
                    v.setIdproducto(cod);
                    v.setDescripcionP(descripcion);
                    v.setPrecio(precio);
                    v.setCantidad(cant);
                    v.setSubtotal(subtotal);
                    lista.add(v);

                    for (int i = 0; i < lista.size(); i++) {
                        total += lista.get(i).getSubtotal();
                    }
                    request.setAttribute("total", total);
                    request.setAttribute("lista", lista);
                    break;
                case "GenerarVenta":
                    //ACTUALIZAR STOCK
                    for (int i = 0; i < lista.size(); i++) {
                        Paciente p = new Paciente();
                        int cantidad = lista.get(i).getCantidad();
                        int idproducto = lista.get(i).getIdproducto();
                        PacienteDAO aO = new PacienteDAO();
                        p = aO.buscar(idproducto);
                        int sac = p.getStock() - cantidad;
                        aO.actualizarstock(idproducto, sac);
                    }
                    
                    //GUARDAR VENTA
                    v.setIdcliente(c.getId());
                    v.setIdempleado(1);
                    v.setNumserie(numeroserie);
                    v.setFecha("2019-06-14");
                    v.setMonto(total);
                    v.setEstado("1");
                    vdao.guardarVenta(v);
                    
                    //GUARDAR DETALLE VENTAS
                    int idv = Integer.parseInt(vdao.IdVentas());
                    for (int i = 0; i < lista.size(); i++) {
                        v = new Notificacion();
                        v.setId(idv);
                        v.setIdproducto(lista.get(i).getIdproducto());
                        v.setCantidad(lista.get(i).getCantidad());
                        v.setPrecio(lista.get(i).getPrecio());
                        vdao.guardarDetalleventa(v);

                    }

                    break;
                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    pdao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=NuevaVenta&accion=Listar").forward(request, response);
                    break;

                default:
                    v = new Notificacion();
                    lista = new ArrayList<>();
                    item = 0;
                    total = 0.0;

                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "0000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        int incrementar = Integer.parseInt(numeroserie);
                        GenerarSerie gs = new GenerarSerie();
                        numeroserie = gs.NumeroSerie(incrementar);
                        request.setAttribute("nserie", numeroserie);
                    }
                   /* request.getRequestDispatcher("RegistrarNotificacion.jsp").forward(request, response);*/
 /*}
            request.getRequestDispatcher("RegistrarNotificacion.jsp").forward(request, response);
        }*/
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
