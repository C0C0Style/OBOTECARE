<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<%
    HttpSession sesion = request.getSession();
    Usuario emp = (Usuario) sesion.getAttribute("user");

    if (emp != null) {
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acudientes</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="d-flex p-4">
        <!-- Formulario para agregar o actualizar acudiente -->
        <div class="card col-sm-4 mr-4">
            <div class="card-body">
                <h5 class="card-title">Registrar / Editar Acudiente</h5>
                <form action="Controlador?menu=Acudiente" method="POST">
                    <div class="form-group">
                        <label>Documento</label>
                        <input type="text" value="${cliente.documento}" name="txtDocumento" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Nombres</label>
                        <input type="text" value="${cliente.nombres}" name="txtNombres" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Apellidos</label>
                        <input type="text" value="${cliente.apellidos}" name="txtApellidos" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Teléfono</label>
                        <input type="text" value="${cliente.telefono}" name="txtTelefono" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Correo</label>
                        <input type="email" value="${cliente.correo}" name="txtCorreo" class="form-control">
                    </div>

                    <div class="d-flex justify-content-between">
                        <input type="submit" name="accion" value="Agregar" class="btn btn-info">
                        <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                    </div>
                </form>
            </div>
        </div>

        <!-- Tabla de acudientes -->
        <div class="col-sm-8">
            <h5 class="mb-3">Lista de Acudientes</h5>
            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Documento</th>
                        <th>Nombres</th>
                        <th>Apellidos</th>
                        <th>Teléfono</th>
                        <th>Correo</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cl" items="${clientes}">
                        <tr>
                            <td>${cl.id}</td>
                            <td>${cl.documento}</td>
                            <td>${cl.nombres}</td>
                            <td>${cl.apellidos}</td>
                            <td>${cl.telefono}</td>
                            <td>${cl.correo}</td>
                            <td>
                                <a class="btn btn-warning btn-sm" href="Controlador?menu=Acudiente&accion=Editar&id=${cl.id}">Editar</a>
                                <a class="btn btn-danger btn-sm" href="Controlador?menu=Acudiente&accion=Borrar&id=${cl.id}">Borrar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
<%
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>
</html>
