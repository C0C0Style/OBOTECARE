<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modelo.Profesional" %>
<%
    // Obtener la sesión existente sin crear una nueva si no existe
    HttpSession sesion = request.getSession(false);

    // Obtener el atributo "user" de la sesión
    Object userInSession = (sesion != null) ? sesion.getAttribute("user") : null;

    // Verificar si hay un usuario en sesión
    if (userInSession == null) {
        // Si no hay usuario, redirigir a la página de inicio de sesión (index.jsp)
        // y detener la ejecución de este JSP.
        response.sendRedirect("index.jsp");
        return;
    }
    // Si el usuario está logueado, el resto del JSP se ejecutará.
    // No necesitamos un bloque `if` que envuelva todo el HTML aquí,
    // ya que la redirección maneja el caso de no-sesión.
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Profesionales</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          crossorigin="anonymous">
</head>
<body>
    <div class="d-flex">
        <div class="card col-sm-4">
            <div class="card-body">
                <form action="Controlador?menu=Profesional" method="POST">
                    <div class="form-group">
                        <label>DNI</label>
                        <input type="text" name="txtDni" value="${empleado.dni}" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Nombres</label>
                        <input type="text" name="txtNombres" value="${empleado.nom}" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Teléfono</label>
                        <input type="text" name="txtTel" value="${empleado.tel}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Estado</label>
                        <select name="txtEstado" class="form-control" required>
                            <option value="1" ${empleado.estado == '1' ? 'selected' : ''}>Activo</option>
                            <option value="2" ${empleado.estado == '2' ? 'selected' : ''}>Inactivo</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Usuario</label>
                        <input type="text" name="txtUsuario" value="${empleado.user}" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Contraseña</label>
                        <input type="password" name="txtPass" class="form-control">
                        <small class="form-text text-muted">Deja en blanco para no cambiar la contraseña.</small>
                    </div>
                    <input type="submit" name="accion" value="Agregar" class="btn btn-info">
                    <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                </form>
            </div>
        </div>

        <div class="col-sm-8">
            <%-- Mensajes de éxito o error --%>
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success mt-3" role="alert">
                    ${mensaje}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3" role="alert">
                    ${error}
                </div>
            </c:if>

            <table class="table table-hover mt-3">
    <thead>
        <tr>
            <th>DNI</th>
            <th>Nombres</th>
            <th>Teléfono</th>
            <th>Estado</th>
            <th class="text-center">Acciones</th>
            
        </tr>
    </thead>
    <tbody>
        <%-- Iterar sobre la lista de profesionales --%>
        <c:forEach var="prof" items="${profesionales}">
            <tr>
                <td>${prof.dni}</td>
                <td>${prof.nom}</td>
                <td>${prof.tel}</td>
                <td>
                    <c:choose>
                        <c:when test="${prof.estado == '1'}">Activo</c:when>
                        <c:otherwise>Inactivo</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <a href="Controlador?menu=Profesional&accion=Editar&id=${prof.id}" class="btn btn-warning btn-sm">Editar</a>
                    <a href="Controlador?menu=Profesional&accion=Borrar&id=${prof.id}" class="btn btn-danger btn-sm"
                       onclick="return confirm('¿Estás seguro de que quieres eliminar este profesional?');">Eliminar</a>
                       <div class="form-group">
                        <a href="Controlador?menu=Profesional&accion=Pacientes" class="btn btn-primary">Ver lista de pacientes</a>
                    </div>
                </td>
                 
            </tr>
        </c:forEach>
    </tbody>
</table>
        </div>
    </div>
</body>
</html>