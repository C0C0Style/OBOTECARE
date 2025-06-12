
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>INICIO</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .navbar {
            background-color: #343a40 !important;
        }
        .dropdown-menu {
            min-width: auto !important;
        }
        .iframe-container {
            height: 650px;
            margin: 20px;
        }

    </style>
</head>
<body > 
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="Principal.jsp">Welcome</a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a style="margin-left: 10px; border: none" class="btn btn-outline-light" href="Controlador?menu=Producto&accion=Listar" target="myFrame">Paciente</a>
                </li>

                <li class="nav-item">
                    <a style="margin-left: 10px; border: none" class="btn btn-outline-light" href="Controlador?menu=Empleado&accion=Listar" target="myFrame">Profesional</a>
                </li>

                <li class="nav-item">
                    <a style="margin-left: 10px; border: none" class="btn btn-outline-light" href="Controlador?menu=Cliente&accion=Listar" target="myFrame">Acudientes</a>
                </li>

                <li class="nav-item">
                    <a style="margin-left: 10px; border: none" class="btn btn-outline-light" href="Controlador?menu=NuevaVenta&accion=default" target="myFrame">Notificaciones</a>
                </li>
            </ul>
        </div>

        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                ${user.getNom()}
            </button>

            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="#"><img src="img/user.png" alt="50" width="50"/>    ${user.getUser()}</a>
                <a class="dropdown-item" href="#">${user.getUser()}@mail.com</a>
                <div class="dropdown-divider"></div>
                <form action="Validar" method="POST">
                    <button name="accion" value="Cerrar Sesión" class="dropdown-item">Cerrar Sesión</button>
                </form>
            </div>
        </div>
    </nav>
               
<center><img src="img/1.png" style="text-align:center;" alt="alt"/></center>
    
    <div class="iframe-container">
        <iframe name="myFrame" style="height: 100%; width: 100%; border: none"></iframe>    
    </div>

    <div class="gif-container"></div>

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</body>

<%
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>
</html>
