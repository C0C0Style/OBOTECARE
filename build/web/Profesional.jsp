<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modelo.Profesional" %>

<%
    HttpSession sesion = request.getSession();
    Profesional emp = (Profesional) sesion.getAttribute("user");

    if (emp == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EMPLEADOS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
          crossorigin="anonymous">
</head>
<body>
    <div class="d-flex">
        <!-- Formulario -->
        <div class="card col-sm-4">
            <div class="card-body">
                <form action="Controlador?menu=Profesional" method="POST">
                    <div class="form-group">
                        <label>DNI</label>
                        <input type="text" name="txtDni" value="${empleado.getDni()}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Nombres</label>
                        <input type="text" name="txtNombres" value="${empleado.getNom()}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Teléfono</label>
                        <input type="text" name="txtTel" value="${empleado.getTel()}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Estado</label>
                        <select name="txtEstado" class="form-control" required>
                    <option value="1">Activo</option>
                    <option value="2">Inactivo</option>
                    </select>
                    </div>
                    <div class="form-group">
                        <label>Usuario</label>
                        <input type="text" name="txtUsuario" value="${empleado.getUser()}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Contraseña</label>
                        <input type="password" name="txtPass" value="${empleado.getPass()}" class="form-control">
                    </div>
                    <input type="submit" name="accion" value="Agregar" class="btn btn-info">
                    <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                </form>
            </div>
        </div>

        <!-- Tabla de empleados -->
        <div class="col-sm-8">
            <table class="table table-hover">
              <thead>
        <tr>
            <th>ID</th>
            <th>DNI</th>
            <th>Nombres</th>
            <th>Teléfono</th>
            <th>Estado</th>
            <th>Usuario</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="prof" items="${profesionales}"> <!-- Nombre debe coincidir -->
            <tr>
                <td>${prof.id}</td>
                <td>${prof.dni}</td>
                <td>${prof.nom}</td>
                <td>${prof.tel}</td>
                <td>${prof.estado == '1' ? 'Activo' : 'Inactivo'}</td>
                <td>${prof.user}</td>
                <td>
                    <a href="Controlador?menu=Profesional&accion=Editar&id=${prof.id}" 
                       class="btn btn-warning">Editar</a>
                    <a href="Controlador?menu=Profesional&accion=Borrar&id=${prof.id}" 
                       class="btn btn-danger"
                       onclick="return confirm('¿Eliminar este profesional?')">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
        </div>
    </div>

    <!-- Scripts de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
