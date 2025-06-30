<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>

<%
    HttpSession sesion = request.getSession();
    Usuario emp = (Usuario) sesion.getAttribute("user");

    if (emp != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>INICIO</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- CSS personalizado -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css">

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" 
              integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

        <!-- Estilo adicional interno para botones pill -->
        <style>
            .btn-pill {
                border-radius: 50px;
                padding: 6px 16px;
                font-weight: 500;
                transition: background-color 0.3s ease;
            }

            .btn-pill:hover {
                background-color: #e3f2fd;
                color: #007bff;
            }

            .main-buttons a {
                margin: 10px;
                border-radius: 25px;
                font-weight: 500;
                padding: 10px 20px;
            }

            .iframe-container {
                width: 100%;
                max-width: 100vw;                /* Usa todo el ancho visible del viewport */
                height: calc(100vh - 60px);      /* Ajusta la altura, puedes bajarlo si ves scroll vertical */
                padding: 0 20px;                 /* Pequeño espacio lateral para evitar desbordes */
                box-sizing: border-box;         /* Incluye padding en el ancho total */
                margin: 0 auto;
                overflow: hidden;               /* Oculta cualquier desborde por seguridad */
            }



            body {
                background-color: #f9fbfd;
            }
        </style>
    </head>
    <body> 
        <!-- NAVBAR MODERNO -->
        <nav class="navbar navbar-expand-lg" style="background: linear-gradient(to right, #74ebd5, #ACB6E5); padding: 10px 20px;">
            <div class="container-fluid d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                    <a class="navbar-brand text-white font-weight-bold" href="Principal.jsp" id="homeLink" style="font-size: 22px;">🏠 Oboete Care</a>
                    <ul class="navbar-nav ml-3 d-flex flex-row">
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm btn-pill ml-2" href="Controlador?menu=Paciente&accion=Listar" target="myFrame">Paciente</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm btn-pill ml-2" href="Controlador?menu=Acudiente&accion=Listar" target="myFrame">Acudientes</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm btn-pill ml-2" href="Controlador?menu=Notificaciones&accion=Listar" target="myFrame">Notificaciones</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm btn-pill ml-2" href="Controlador?menu=Profesional&accion=Listar" target="myFrame">Profesional</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm btn-pill ml-2" href="Controlador?menu=Usuario&accion=Listar" target="myFrame">Usuarios</a>
                        </li>
                    </ul>
                </div>

                <div class="dropdown">
                    <button class="btn btn-outline-light rounded-pill dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                        👤 ${user.getNom()}
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

        <!-- LOGO CENTRAL -->
    <center>
        <img src="img/logo.png" alt="Banner Principal" class="img-fluid mt-3" style="max-width: 200px; height: auto;" />
    </center>

    <!-- BOTONES DE ACCESO RÁPIDO -->
    <div class="main-buttons container text-center" id="mainButtons">
        <a class="btn btn-primary" href="Controlador?menu=VisionMision&accion=Ver" target="myFrame">Visión y Misión</a>
        <a class="btn btn-primary" href="Controlador?menu=Equipo de trabajo&accion=Ver" target="myFrame">Equipo de Trabajo</a>
        <a class="btn btn-primary" href="Controlador?menu=Trayectoria&accion=Ver" target="myFrame">Trayectoria del Proyecto</a>
        <a class="btn btn-primary" href="Controlador?menu=Contacto&accion=Ver" target="myFrame">Información de Contacto</a>
    </div>

    <!-- CONTENEDOR DE IFRAME -->
    <div class="iframe-container">
        <iframe name="myFrame" id="myFrame" style="height: 100%; width: 100%; border: none;"></iframe>    
    </div>

    <!-- SCRIPTS -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" 
    integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" 
    integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

    <script>
        const iframe = document.getElementById("myFrame");
        const buttonsSection = document.getElementById("mainButtons");
        const homeLink = document.getElementById("homeLink");

        // Oculta los botones al cargar algo en el iframe
        iframe.addEventListener("load", function () {
            const iframeUrl = iframe.contentWindow.location.href;
            if (!iframeUrl.includes("Principal.jsp") && iframeUrl !== "about:blank") {
                buttonsSection.style.display = "none";
            }
        });

        // Muestra botones y limpia iframe al hacer clic en "Home"
        homeLink.addEventListener("click", function (e) {
            e.preventDefault();
            buttonsSection.style.display = "flex";
            buttonsSection.classList.add("main-buttons");
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
