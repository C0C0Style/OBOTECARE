<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modelo.Acudiente"%>
<%@page import="modelo.Usuario"%>

<%
    HttpSession sesion = request.getSession();
    Usuario emp = (Usuario) sesion.getAttribute("user");
    if (emp == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pacientes Asignados</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h3>Pacientes asignados a <strong>${acudiente.nombres} ${acudiente.apellidos}</strong></h3>
    <a href="Controlador?menu=Acudiente&accion=Listar" class="btn btn-secondary btn-sm mb-3">← Volver</a>

    <c:choose>
        <c:when test="${empty pacientesAsignados}">
            <div class="alert alert-info">Este acudiente no tiene pacientes asignados.</div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Documento</th>
                        <th>Nombres</th>
                        <th>Apellidos</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${pacientesAsignados}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.numeroDocumento}</td>
                            <td>${p.nombres}</td>
                            <td>${p.apellidos}</td>
                            <td>${p.correo}</td>
                            <td>${p.telefono}</td>
                            <td>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalNotificar${p.id}">
                                    Programar Notificación
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<!-- Modales de notificación para cada paciente -->
<c:forEach var="p" items="${pacientesAsignados}">
    <div class="modal fade" id="modalNotificar${p.id}" tabindex="-1" role="dialog" aria-labelledby="modalLabel${p.id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="Controlador" method="POST">
                <input type="hidden" name="menu" value="Notificacion">
                <input type="hidden" name="accion" value="Programar">
                <input type="hidden" name="idPaciente" value="${p.id}">

                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalLabel${p.id}">Programar notificación</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Paciente:</strong> ${p.nombres} ${p.apellidos}</p>
                        <div class="form-group">
                            <label>Mensaje</label>
                            <textarea name="mensaje" class="form-control" required></textarea>
                        </div>
                        <div class="form-group">
                            <label>Fecha y hora</label>
                            <input type="datetime-local" name="fechaHora" class="form-control" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Programar</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:forEach>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
