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
        <title>GESTIÓN DE PACIENTES</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f5f8fb;
                font-family: 'Segoe UI', sans-serif;
                padding: 20px;
            }

            .card {
                border-radius: 15px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                background-color: #ffffff;
            }

            .form-control {
                border-radius: 10px;
                border: 1px solid #ced4da;
            }

            label {
                font-weight: 500;
            }

            .btn {
                border-radius: 20px;
                font-weight: 500;
            }

            .btn-info {
                background-color: #4a90e2;
                border: none;
            }

            .btn-info:hover {
                background-color: #357ab8;
            }

            .btn-success {
                background-color: #45cb85;
                border: none;
            }

            .btn-success:hover {
                background-color: #37a76d;
            }

            .btn-danger {
                background-color: #d9534f;
                border: none;
            }

            .btn-danger:hover {
                background-color: #c9302c;
            }

            .table thead th {
                background-color: #4a90e2;
                color: white;
                text-align: center;
            }

            .table td {
                text-align: center;
                vertical-align: middle;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <c:if test="${not empty sessionScope.mensajePaciente}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.mensajePaciente}
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                </div>
                <c:remove var="mensajePaciente" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.errorPaciente}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.errorPaciente}
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                </div>
                <c:remove var="errorPaciente" scope="session"/>
            </c:if>

            <h3 class="mb-4 font-weight-bold text-primary">Registrar / Editar Paciente</h3>

            <div class="card mb-5">
                <div class="card-body">
                    <form action="Controlador?menu=Paciente" method="POST" enctype="multipart/form-data">
                        <c:if test="${not empty paciente.id}">
                            <input type="hidden" name="id" value="${paciente.id}">
                        </c:if>
                        
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Nombres</label>
                                <input type="text" value="${not empty paciente ? paciente.getNombres() : ''}" name="txtNombres" class="form-control" placeholder="Ingrese nombres" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Apellidos</label>
                                <input type="text" value="${not empty paciente ? paciente.getApellidos() : ''}" name="txtApellidos" class="form-control" placeholder="Ingrese apellidos" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Diagnóstico</label>
                                <input type="text" value="${not empty paciente ? paciente.getDiagnostico() : ''}" name="txtDiagnostico" class="form-control" placeholder="Ingrese diagnóstico" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Número de Documento</label>
                                <input type="text" value="${not empty paciente ? paciente.getNumeroDocumento() : ''}" name="txtNumeroDocumento" class="form-control" placeholder="Ingrese número de documento" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Fecha de Nacimiento</label>
                                <input type="date" value="${not empty paciente ? paciente.getFechaNacimiento() : ''}" name="txtFechaNacimiento" class="form-control" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Dirección</label>
                                <input type="text" value="${not empty paciente ? paciente.getDireccion() : ''}" name="txtDireccion" class="form-control" placeholder="Ingrese dirección" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Teléfono</label>
                                <input type="text" value="${not empty paciente ? paciente.getTelefono() : ''}" name="txtTelefono" class="form-control" placeholder="Ingrese teléfono" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Correo</label>
                                <input type="email" value="${not empty paciente ? paciente.getCorreo() : ''}" name="txtCorreo" class="form-control" placeholder="Ingrese correo electrónico" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Historial Médico (imagen o documento)</label>
                            <input type="file" name="txtHistorial" class="form-control">
                            <c:if test="${not empty paciente.getHistorial()}">
                                <p>Archivo actual:
                                    <a href="${pageContext.request.contextPath}/${paciente.getHistorial()}" target="_blank">Ver historial</a>
                                </p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Estado</label>
                            <select name="txtEstado" class="form-control" required>
                                <option value="1" ${not empty paciente && paciente.getEstado() == '1' ? 'selected' : ''}>Activo</option>
                                <option value="2" ${not empty paciente && paciente.getEstado() == '2' ? 'selected' : ''}>Inactivo</option>
                            </select>
                        </div>
                        <div class="text-right">
                            <c:if test="${empty paciente}">
                                <input type="submit" name="accion" value="Agregar" class="btn btn-info mr-2">
                            </c:if>
                            <c:if test="${not empty paciente}">
                                <input type="submit" name="accion" value="Actualizar" class="btn btn-success mr-2">
                            </c:if>
                            <a href="Controlador?menu=Paciente&accion=Listar" class="btn btn-secondary">Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>

            <h3 class="mb-3 font-weight-bold text-primary">Lista de Pacientes</h3>

            <div class="d-flex justify-content-center" style="padding: 0 20px;">
                <table class="table table-hover table-bordered" style="max-width: 1400px; width: 100%;">
                    <thead>
                        <tr>
                            <th>Nombres</th>
                            <th>Apellidos</th>
                            <th>Diagnóstico</th>
                            <th>Documento</th>
                            <th>Teléfono</th>
                            <th>Correo</th>
                            <th>Acudiente</th>
                            <th>Profesional Asignado</th>
                            <th>Historial Médico</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pr" items="${pacientes}">
                            <tr>
                                <td>${pr.getNombres()}</td>
                                <td>${pr.getApellidos()}</td>
                                <td>${pr.getDiagnostico()}</td>
                                <td>${pr.getNumeroDocumento()}</td>
                                <td>${pr.getTelefono()}</td>
                                <td>${pr.getCorreo()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${pr.getIdAcudiente() > 0}">
                                            <span class="badge badge-success">Asignado</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-secondary">No asignado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                    
                                <td>
                                    <c:choose>
                                        <c:when test="${pr.getProfesional() != null && pr.getProfesional().getId() > 0}">
                                            <%-- Muestra el nombre del profesional si está asignado --%>
                                            <span class="badge badge-info">${pr.getProfesional().getNom()}</span>
                                            <a class="btn btn-danger btn-sm ml-2" 
                                               href="Controlador?menu=Paciente&accion=DesvincularProfesional&id=${pr.getId()}" 
                                               onclick="return confirm('¿Estás seguro de que quieres desvincular a ${profAsignado.getNom()} de este paciente?');"
                                               title="Desvincular Profesional">
                                                <i class="fas fa-unlink"></i> Desvincular
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-light">Sin Asignar</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${not empty pr.getHistorial()}">
                                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalHistorial${pr.getId()}">
                                            Consultar
                                        </button>
                                    </c:if>
                                    <c:if test="${empty pr.getHistorial()}">
                                        No hay
                                    </c:if>
                                </td>
                                <td>${pr.getEstado() == '1' ? 'Activo' : 'Inactivo'}</td>
                                <td>
                                    <div class="d-flex justify-content-center">
                                        <a class="btn btn-info btn-sm mr-2" href="Controlador?menu=Paciente&accion=Editar&id=${pr.getId()}">Editar</a>
                                        <a class="btn btn-outline-danger btn-sm mr-2" href="Controlador?menu=Paciente&accion=Borrar&id=${pr.getId()}" onclick="return confirm('¿Estás seguro de que quieres eliminar a ${pr.getNombres()} ${pr.getApellidos()}?');">Borrar</a>
                                        
                                        <%-- Nuevo botón para Asignar Profesional --%>
                                        <a class="btn btn-warning btn-sm" href="Controlador?menu=Paciente&accion=FormAsignarProfesional&id=${pr.getId()}" title="Asignar/Desasignar Profesional">Profesional</a>
                                        
                                        <%-- El botón de Acudiente, si lo quieres mantener o modificar --%>
                                        <a class="btn btn-secondary btn-sm ml-2" href="Controlador?menu=Paciente&accion=FormAsignar&id=${pr.getId()}">Acudiente</a>
                                         <a href="Controlador?menu=Notificaciones&accion=FormAgregar&idPaciente=${pr.getId()}" class="btn btn-success btn-sm mt-1">Añadir Recordatorio</a>
                                        <a href="Controlador?menu=Notificaciones&accion=ListarPorPaciente&idPaciente=${pr.getId()}" class="btn btn-info btn-sm mt-1">Ver Recordatorios</a>
                                    </div>
                                </td>
                            </tr>

                            <div class="modal fade" id="modalHistorial${pr.getId()}" tabindex="-1" role="dialog" aria-labelledby="historialLabel${pr.getId()}" aria-hidden="true">
                                <div class="modal-dialog modal-fullscreen" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-primary text-white">
                                            <h5 class="modal-title" id="historialLabel${pr.getId()}">
                                                Historial Médico de ${pr.getNombres()} ${pr.getApellidos()}
                                            </h5>
                                            <button type="button" class="close text-white" data-dismiss="modal" aria-label="Cerrar">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>

                                        <div class="modal-body d-flex justify-content-center align-items-center" style="min-height: 80vh;">
                                            <c:choose>
                                                <c:when test="${fn:endsWith(pr.getHistorial(), '.pdf')}">
                                                    <iframe src="${pageContext.request.contextPath}/${pr.getHistorial()}" width="100%" height="100%" style="border: none;"></iframe>
                                                    </c:when>

                                                <c:when test="${fn:endsWith(pr.getHistorial(), '.jpg') || fn:endsWith(pr.getHistorial(), '.png') || fn:endsWith(pr.getHistorial(), '.jpeg') || fn:endsWith(pr.getHistorial(), '.gif')}">
                                                    <div class="text-center">
                                                        <img src="${pageContext.request.contextPath}/${pr.getHistorial()}" class="img-fluid mb-3" alt="Historial médico" style="max-height: 80vh;">
                                                        <br/>
                                                        <a href="${pageContext.request.contextPath}/${pr.getHistorial()}" download class="btn btn-outline-primary">
                                                            Descargar Imagen
                                                        </a>
                                                    </div>
                                                </c:when>

                                                <c:otherwise>
                                                    <div class="text-center">
                                                        <p>Tipo de archivo no compatible para vista previa.</p>
                                                        <a href="${pageContext.request.contextPath}/${pr.getHistorial()}" target="_blank" class="btn btn-primary">Descargar archivo</a>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    <% } else {
        // Si no hay sesión, redirigir al login
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } %>
</html>