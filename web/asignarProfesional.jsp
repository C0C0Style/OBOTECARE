<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Asignar Profesional a Paciente</title>
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

            .btn-primary {
                background-color: #007bff;
                border: none;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            .btn-secondary {
                background-color: #6c757d;
                border: none;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h3 class="mb-4 font-weight-bold text-primary">Asignar Profesional a Paciente: ${pacienteSeleccionado.getNombres()} ${pacienteSeleccionado.getApellidos()}</h3>

            <div class="card mb-5">
                <div class="card-body">
                    <form action="Controlador?menu=Paciente" method="POST">
                        <input type="hidden" name="idPaciente" value="${pacienteSeleccionado.getId()}">

                        <div class="form-group">
                            <label for="idProfesional">Seleccionar Profesional:</label>
                            <select class="form-control" id="idProfesional" name="idProfesional">
                                <option value="0">-- Seleccionar Profesional --</option>
                                <c:forEach var="pro" items="${profesionales}">
                                    <option value="${pro.getId()}" ${pacienteSeleccionado.getIdProfesional() == pro.getId() ? 'selected' : ''}>
                                        ${pro.getNom()} (${pro.getDni()})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="text-right">
                            <input type="submit" name="accion" value="AsignarProfesional" class="btn btn-primary mr-2">
                            <a href="Controlador?menu=Paciente&accion=listar" class="btn btn-secondary">Cancelar</a>
                            <c:if test="${pacienteSeleccionado.getIdProfesional() > 0}">
                                <input type="submit" name="accion" value="DesasignarProfesional" class="btn btn-danger ml-2">
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    <% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } %>
</html>