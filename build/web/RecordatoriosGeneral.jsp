<%-- RecordatoriosGeneral.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Historial General de Recordatorios</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { padding-top: 20px; }
        .container { max-width: 1200px; }
        .table th, .table td { vertical-align: middle; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Historial General de Recordatorios</h1>
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

        

        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID Rec.</th>
                        <th>Paciente</th>
                        <th>Fecha y Hora</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        
                        <th>Fecha Creación</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rec" items="${requestScope.recordatorios}">
                        <tr>
                            <td>${rec.idRecordatorio}</td>
                            <td>${rec.paciente.nombres} ${rec.paciente.apellidos}</td>
                            <td>${rec.fechaRecordatorio}</td>
                            <td>${rec.descripcion}</td>
                            <td>${rec.estado}</td>
                            
                            <td>${rec.fechaCreacion}</td>
                            <td>
                                <a href="Controlador?menu=Notificaciones&accion=Editar&id=${rec.idRecordatorio}" class="btn btn-warning btn-sm">Editar</a>
                                <%-- CONDICIÓN PARA MOSTRAR EL BOTÓN DE ELIMINAR --%>
                    <c:if test="${rec.getEstado() eq 'Completado' || rec.getEstado() eq 'Cancelado'}">
                        <a href="Controlador?menu=Notificaciones&accion=Borrar&id=${rec.getIdRecordatorio()}" 
                           class="btn btn-danger btn-sm" 
                           onclick="return confirm('¿Estás seguro de que quieres eliminar este recordatorio?');">
                            <i class="fas fa-trash-alt"></i> Eliminar
                        </a>
                    </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty requestScope.recordatorios}">
                        <tr>
                            <td colspan="8">No hay recordatorios registrados.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>