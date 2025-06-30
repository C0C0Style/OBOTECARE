<%-- RecordatorioForm.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty requestScope.recordatorio}">Editar Recordatorio</c:when>
            <c:otherwise>Añadir Nuevo Recordatorio</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { padding-top: 20px; }
        .container { max-width: 800px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${not empty requestScope.recordatorio}">Editar Recordatorio</c:when>
                <c:otherwise>Añadir Nuevo Recordatorio</c:otherwise>
            </c:choose>
        </h1>
        <hr>

        <%-- Mensajes de éxito/error --%>
        <c:if test="${not empty sessionScope.mensaje}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${sessionScope.mensaje}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <c:remove var="mensaje" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${sessionScope.error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <form action="Controlador" method="POST">
            <input type="hidden" name="menu" value="Notificaciones">
            
            <c:choose>
                <c:when test="${not empty requestScope.recordatorio}">
                    <input type="hidden" name="accion" value="Actualizar">
                    <input type="hidden" name="idRecordatorio" value="${requestScope.recordatorio.idRecordatorio}">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="accion" value="Agregar">
                </c:otherwise>
            </c:choose>

            <div class="form-group">
                <label for="idPaciente">Paciente:</label>
                <c:choose>
                    <c:when test="${not empty requestScope.pacienteSeleccionado}">
                        <input type="text" class="form-control" value="${requestScope.pacienteSeleccionado.nombres} ${requestScope.pacienteSeleccionado.apellidos}" readonly>
                        <input type="hidden" name="idPaciente" value="${requestScope.pacienteSeleccionado.id}">
                    </c:when>
                    <c:when test="${not empty requestScope.recordatorio}">
                        <select class="form-control" id="idPaciente" name="idPaciente" required>
                            <c:forEach var="pac" items="${requestScope.listaPacientes}">
                                <option value="${pac.id}" ${pac.id == requestScope.recordatorio.idPaciente ? 'selected' : ''}>
                                    ${pac.nombres} ${pac.apellidos}
                                </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select class="form-control" id="idPaciente" name="idPaciente" required>
                            <option value="">Seleccione un paciente...</option>
                            <c:forEach var="pac" items="${requestScope.listaPacientes}">
                                <option value="${pac.id}">${pac.nombres} ${pac.apellidos}</option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="txtFechaHora">Fecha y Hora:</label>
                <input type="datetime-local" class="form-control" id="txtFechaHora" name="txtFechaHora" 
                       value="<c:out value="${not empty requestScope.recordatorio ? recordatorio.fechaRecordatorio : ''}" />" required>
            </div>

            <div class="form-group">
                <label for="txtDescripcion">Descripción:</label>
                <textarea class="form-control" id="txtDescripcion" name="txtDescripcion" rows="3" required><c:out value="${not empty requestScope.recordatorio ? recordatorio.descripcion : ''}" /></textarea>
            </div>

            <div class="form-group">
                <label for="txtEstado">Estado:</label>
                <select class="form-control" id="txtEstado" name="txtEstado" required>
                    <option value="Pendiente" ${recordatorio.estado eq 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                    <option value="Completado" ${recordatorio.estado eq 'Completado' ? 'selected' : ''}>Completado</option>
                    <option value="Cancelado" ${recordatorio.estado eq 'Cancelado' ? 'selected' : ''}>Cancelado</option>
                </select>
            </div>

            <div class="form-group">
                
                <c:choose>
                    <c:when test="${not empty requestScope.idProfesionalCreador}">
                        
                    </c:when>
                    <c:otherwise>
                        <select class="form-control" id="idProfesionalCreador" name="idProfesionalCreador">
                            <option value="">-- Sin asignar --</option>
                            <c:forEach var="prof" items="${requestScope.listaProfesionales}">
                                <option value="${prof.id}" ${prof.id == recordatorio.idProfesionalCreador ? 'selected' : ''}>
                                    ${prof.nom}
                                </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${not empty requestScope.recordatorio}">Actualizar Recordatorio</c:when>
                    <c:otherwise>Guardar Recordatorio</c:otherwise>
                </c:choose>
            </button>
            <c:choose>
                <c:when test="${not empty requestScope.recordatorio && not empty requestScope.recordatorio.idPaciente}">
                    <a href="Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=${requestScope.recordatorio.idPaciente}" class="btn btn-secondary">Cancelar y Volver</a>
                </c:when>
                <c:when test="${not empty requestScope.pacienteSeleccionado}">
                     <a href="Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=${requestScope.pacienteSeleccionado.id}" class="btn btn-secondary">Cancelar y Volver</a>
                </c:when>
                <c:otherwise>
                    <a href="Controlador?menu=Notificaciones&accion=Listar" class="btn btn-secondary">Cancelar y Volver</a>
                </c:otherwise>
            </c:choose>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>