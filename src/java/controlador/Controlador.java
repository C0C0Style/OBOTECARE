package controlador;

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
                    
                    if(request.getParameter("txtEstado").equals("Activo"))
                        est = "1";
                    else
                        est = "2";
                        
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
                    
                    if(request.getParameter("txtEstado").equals("Activo"))
                        est2 = "1";
                    else
                        est2 = "2";
                    
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

        if (menu.equals("Cliente")) {
            switch (accion) {
                case "Listar":
                List<Acudiente> listaClientes = cdao.listar();
                request.setAttribute("clientes", listaClientes);
                break;


                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtName");
                    String dir = request.getParameter("txtDir");
                    String est;
                    
                    if(request.getParameter("txtStt").equals("Activo"))
                        est = "1";
                    else
                        est = "2";

                    cl.setDni(dni);
                    cl.setNombres(nom);
                    cl.setDireccion(dir);
                    cl.setEstado(est);

                    cdao.agregar(cl);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Acudiente c = cdao.listarId(ide);
                    request.setAttribute("cliente", c);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String dni2 = request.getParameter("txtDni");
                    String nom2 = request.getParameter("txtName");
                    String dir2 = request.getParameter("txtDir");
                    String est2;
                    
                    if(request.getParameter("txtStt").equals("Activo"))
                        est2 = "1";
                    else
                        est2 = "2";

                    cl.setDni(dni2);
                    cl.setNombres(nom2);
                    cl.setDireccion(dir2);
                    cl.setEstado(est2);
                    cl.setId(ide);

                    cdao.actualizar(cl);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    cdao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Acudiente.jsp").forward(request, response);
        }

        if (menu.equals("Producto")) {
            switch (accion) {
                case "Listar":
                    List<Paciente> listaProductos = pdao.listar();
                    request.setAttribute("productos", listaProductos);
                    break;


                case "Agregar":
                    String desc = request.getParameter("txtDes");
                    String prec = request.getParameter("txtPrecio");
                    String stock = request.getParameter("txtSto");
                    String est;
                    double precio=0.0;
                    
                    if(request.getParameter("txtStt").equals("Disponible"))
                        est = "1";
                    else
                        est = "2";
                    if (prec != null && !prec.trim().isEmpty()) {
                        try {
                            precio = Double.parseDouble(prec);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: No se pudo convertir el precio a número.");
                        }
                    }

                    pr.setPrecio(precio);
                    pr.setDescripcion(desc);
                   /* pr.setPrecio(Double.parseDouble(prec));*/
                    pr.setStock(Integer.parseInt(stock));
                    pr.setEstado(est);

                    pdao.agregar(pr);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Paciente p = pdao.listarId(ide);
                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String desc2 = request.getParameter("txtDes");
                    String prec2 = request.getParameter("txtPrecio");
                    String stock2 = request.getParameter("txtSto");
                    String est2;
                    
                    if(request.getParameter("txtStt").equals("Disponible"))
                        est2 = "1";
                    else
                        est2 = "2";

                    pr.setDescripcion(desc2);
                    pr.setPrecio(Double.parseDouble(prec2));
                    pr.setStock(Integer.parseInt(stock2));
                    pr.setEstado(est2);
                    pr.setId(ide);

                    pdao.actualizar(pr);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Borrar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    pdao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Paciente.jsp").forward(request, response);
        }

        if (menu.equals("NuevaVenta")) {
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
            }
            request.getRequestDispatcher("RegistrarNotificacion.jsp").forward(request, response);
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
