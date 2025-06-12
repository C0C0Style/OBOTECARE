<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "modelo.Profesional"%>

<%
    HttpSession sesion = request.getSession();
    Profesional emp = (Profesional) sesion.getAttribute("user");

    if(emp != null) {
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>INICIO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- CSS externo -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css">

    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" 
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
</head>
<body> 
    <!-- NAVBAR -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
                <a class="navbar-brand" href="Principal.jsp" id="homeLink">Home</a>
                <ul class="navbar-nav ml-3 d-flex flex-row">
                    <li class="nav-item">
                        <a class="btn btn-outline-light ml-2" href="Controlador?menu=Producto&accion=Listar" target="myFrame">Paciente</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light ml-2" href="Controlador?menu=Empleado&accion=Listar" target="myFrame">Profesional</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light ml-2" href="Controlador?menu=Cliente&accion=Listar" target="myFrame">Acudientes</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light ml-2" href="Controlador?menu=NuevaVenta&accion=default" target="myFrame">Notificaciones</a>
                    </li>
                </ul>
            </div>

            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                    ${user.getNom()}
                </button>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" href="#"><img src="img/user.png" alt="Usuario" width="36"/> ${user.getUser()}</a>
                    <a class="dropdown-item" href="#">${user.getUser()}@mail.com</a>
                    <div class="dropdown-divider"></div>
                    <form action="Validar" method="POST">
                        <button name="accion" value="Cerrar Sesión" class="dropdown-item">Cerrar Sesión</button>
                    </form>
                </div>
            </div>
        </div>
    </nav>

    <!-- Imagen principal -->
    <center><img src="img/logo.png" alt="Banner Principal" class="img-fluid mt-3" style="max-width: 300px; height: auto;"/></center>


    <!-- Botones principales -->
    <div class="main-buttons container text-center" id="mainButtons">
        <a class="btn btn-primary" href="Controlador?menu=VisionMision&accion=Ver" target="myFrame">Visión y Misión</a>
        <a class="btn btn-primary" href="Controlador?menu=Equipo de trabajo&accion=Ver" target="myFrame">Equipo de Trabajo</a>
        <a class="btn btn-primary" href="Controlador?menu=Trayectoria&accion=Ver" target="myFrame">Trayectoria del Proyecto</a>
        <a class="btn btn-primary" href="Controlador?menu=Contacto&accion=Ver" target="myFrame">Información de Contacto</a>
    </div>

    <!-- Contenedor del iframe -->
    <div class="iframe-container">
        <iframe name="myFrame" id="myFrame" style="height: 100%; width: 100%; border: none;"></iframe>    
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" 
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

    <script>
        const iframe = document.getElementById("myFrame");
        const buttonsSection = document.getElementById("mainButtons");
        const homeLink = document.getElementById("homeLink");

        // Oculta botones al cargar algo en el iframe
        iframe.addEventListener("load", function () {
            const iframeUrl = iframe.contentWindow.location.href;
            if (!iframeUrl.includes("Principal.jsp") && iframeUrl !== "about:blank") {
                buttonsSection.style.display = "none";
            }
        });

        // Muestra botones y limpia el iframe al hacer clic en "Welcome"
        homeLink.addEventListener("click", function (e) {
            e.preventDefault();
            buttonsSection.style.display = "flex";
            buttonsSection.classList.add("main-buttons"); // Fuerza clase para estilos
            iframe.src = "";
        });
    </script>
</body>
<%
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>
</html>
