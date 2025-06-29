<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- ¡Esta línea es la clave! --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Pacientes</title>
    <%-- Si usas Bootstrap en otras páginas, es buena idea incluirlo aquí también para mantener la consistencia --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          crossorigin="anonymous">
</head>
<body>
    <div class="container mt-4"> <%-- Envuelve el contenido en un contenedor para mejor diseño --%>
        <h1>Lista de Pacientes</h1>
        <table class="table table-hover"> <%-- Añade clases de Bootstrap a la tabla --%>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>DNI</th>
                    <th>Teléfono</th>
                    <th>Correo</th>
                    <th>Dirección</th>
                    <th>Diagnóstico</th>
                    <th>Estado</th> <%-- Cambié "Acudiente" por "Estado" ya que el campo es ${paciente.estado} --%>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="paciente" items="${pacientes}">
                    <tr>
                        <td>${paciente.id}</td>
                        <td>${paciente.nombres}</td>
                        <td>${paciente.apellidos}</td>
                        <td>${paciente.numeroDocumento}</td>
                        <td>${paciente.telefono}</td>
                        <td>${paciente.correo}</td>
                        <td>${paciente.direccion}</td>
                        <td>${paciente.diagnostico}</td>
                        <td>${paciente.estado}</td> <%-- Aquí es donde estabas mostrando el estado, no el acudiente --%>
                    </tr>
                </c:forEach>
                <c:if test="${empty pacientes}">
                    <tr>
                        <td colspan="9" class="text-center">No hay pacientes registrados.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <a href="Controlador?menu=Profesional&accion=Listar" class="btn btn-secondary">Volver a Profesionales</a>
    </div>
    <%-- Opcional: scripts de Bootstrap si los necesitas para funcionalidades interactivas --%>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>