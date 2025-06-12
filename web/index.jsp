<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>INGRESO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
          crossorigin="anonymous">
    <style>
        body {
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
        }

        .card-body {
            padding: 2rem;
        }

        .form-group img {
            width: 100px;
            height: 100px;
            object-fit: contain;
        }

        .btn-primary {
            border-radius: 25px;
        }

        label {
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="card col-md-4">
    <div class="card-body">
        <form action="Validar" method="POST">
            <div class="form-group text-center">
                <img src="img/login.png" alt="Login">
                <h4 class="mt-2">Inicio de Sesión</h4>
            </div>

            <div class="form-group">
                <label for="txtuser">Usuario:</label>
                <input type="text" id="txtuser" name="txtuser" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="txtpass">Contraseña:</label>
                <input type="password" id="txtpass" name="txtpass" class="form-control" required>
            </div>

            <input type="submit" name="accion" value="Ingreso" class="btn btn-primary btn-block">
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
        crossorigin="anonymous"></script>
</body>
</html>
