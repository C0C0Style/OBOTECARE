<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Asignar Acudiente</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <script>
            function buscarAcudiente() {
                const documento = document.getElementById("documento").value;
                if (documento.trim() === "")
                    return;

                fetch("Controlador?menu=Paciente&accion=BuscarDatosAcudiente&documento=" + documento)
                        .then(res => res.json())
                        .then(data => {
                            if (data.telefono && data.nombre) {
                                document.getElementById("telefonoContacto").value = data.telefono;
                                document.getElementById("nombreAcudiente").value = data.nombre;
                                document.getElementById("infoAcudiente").classList.remove("d-none");
                                document.getElementById("btnAsignar").classList.remove("d-none");
                                document.getElementById("btnBuscar").classList.add("d-none");
                                document.getElementById("documento").readOnly = true;
                            } else {
                                alert("No se encontró un acudiente con ese número de documento");
                            }
                        });
            }

            function cancelarBusqueda() {
                window.location.href = "Controlador?menu=Paciente&accion=Listar";
            }

            function eliminarAsignacion() {
                if (confirm("¿Está seguro de eliminar el acudiente asignado?")) {
                    window.location.href = "Controlador?menu=Paciente&accion=EliminarAcudiente&idPaciente=${paciente.id}";
                }
            }
        </script>
    </head>
    <body class="p-5">
        <div class="container">
            <h3 class="mb-4 text-primary">Gestión de Acudiente del Paciente</h3>

            <c:if test="${paciente.idAcudiente != null && paciente.idAcudiente > 0}">
                <div class="alert alert-info">
                    Este paciente ya tiene un acudiente asignado.
                </div>

                <p><strong>Documento:</strong> ${paciente.acudiente.documento}</p>
                <p><strong>Nombre:</strong> ${paciente.acudiente.nombres} ${paciente.acudiente.apellidos}</p>
                <p><strong>Teléfono:</strong> ${paciente.acudiente.telefono}</p>
                <p><strong>Parentesco:</strong> ${paciente.parentesco}</p>

                <button class="btn btn-danger" onclick="eliminarAsignacion()">Eliminar</button>
                <button class="btn btn-secondary ml-2" onclick="cancelarBusqueda()">Cancelar</button>

            </c:if>

            <c:if test="${paciente.idAcudiente == null || paciente.idAcudiente == 0}">
                <form action="Controlador?menu=Paciente&accion=AsignarAcudiente" method="POST">
                    <input type="hidden" name="idPaciente" value="${paciente.id}" />

                    <div class="form-group">
                        <label>Documento del Acudiente</label>
                        <input type="text" id="documento" name="documento" class="form-control" required>
                    </div>

                    <button type="button" id="btnBuscar" onclick="buscarAcudiente()" class="btn btn-primary">Buscar</button>
                    <button type="button" class="btn btn-secondary ml-2" onclick="cancelarBusqueda()">Cancelar</button>

                    <div id="infoAcudiente" class="mt-4 d-none">
                        <div class="form-group">
                            <label>Nombre del Acudiente</label>
                            <input type="text" id="nombreAcudiente" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label>Teléfono de Contacto</label>
                            <input type="text" id="telefonoContacto" name="telefonoContacto" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label>Parentesco</label>
                            <input type="text" name="parentesco" class="form-control" required>
                        </div>
                        <button type="submit" id="btnAsignar" class="btn btn-success d-none">Asignar</button>
                    </div>
                </form>
            </c:if>

            <c:if test="${not empty mensaje}">
                <div class="alert alert-success mt-3">${mensaje}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
        </div>
    </body>
</html>