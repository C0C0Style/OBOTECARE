<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "modelo.Profesional"%>
<%
    HttpSession sesion = request.getSession();
    Profesional emp = (Profesional) sesion.getAttribute("user");
    
    if(emp != null) {
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <title>Acudiente</title>
</head>
<body>
    <div class="d-flex">
        <!-- Formulario para agregar o actualizar cliente -->
        <div class="card col-sm-4">
            <div class="card_body">
                <form action="Controlador?menu=Cliente" method="POST">
                    <div class="form-group">
                        <label>DNI</label>
                        <input type="text" value="${cliente.getDni()}" name="txtDni" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>Nombres</label>
                        <input type="text" value="${cliente.getNombres()}" name="txtName" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>Dirección</label>
                        <input type="text" value="${cliente.getDireccion()}" name="txtDir" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>Estado</label>
                        <select name="txtStt" class="form-control">
                            <option></option>
                            <option>Activo</option>
                            <option>Inactivo</option>
                        </select>
                    </div>
                    
                    <!-- Botones para enviar el formulario -->
                    <input type="submit" name="accion" value="Agregar" class="btn btn-info">
                    <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                </form>
            </div>
        </div>

        <!-- Tabla de clientes -->
        <div class="col-sm-8">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>DNI</th>
                        <th>NOMBRES</th>
                        <th>DIRECCIÓN</th>
                        <th>ESTADO</th>
                        <th>ACCIONES</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de clientes -->
                    <c:forEach var="cl" items="${clientes}">
                        <tr>
                            <td>${cl.getId()}</td>
                            <td>${cl.getDni()}</td>
                            <td>${cl.getNombres()}</td>
                            <td>${cl.getDireccion()}</td>
                            <td>${cl.getEstado()}</td>
                            <!-- Botones para editar y eliminar cliente -->
                            <td>
                                <a class="btn btn-warning" href="Controlador?menu=Cliente&accion=Editar&id=${cl.getId()}">Editar</a>
                                <a class="btn btn-danger" href="Controlador?menu=Cliente&accion=Borrar&id=${cl.getId()}">Borrar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Scripts de Bootstrap y jQuery -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>   
</body>
<%
    } else {
        // Si no hay empleado autenticado, redireccionar a la página de inicio de sesión
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>
</html>
