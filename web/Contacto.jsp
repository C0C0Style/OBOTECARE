<%-- 
    Document   : Contacto
    Created on : 12/06/2025, 2:25:39 p. m.
    Author     : torre
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contacto - Oboete Care</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 40px 20px;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background-color: #ffffff;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
        }

        .btn-home.back-arrow {
            display: inline-flex;
            align-items: center;
            background-color: #0046af;
            color: white;
            font-size: 16px;
            font-weight: 500;
            padding: 10px 24px;
            border-radius: 999px;
            text-decoration: none;
            border: none;
            margin-bottom: 40px;
            transition: background-color 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .btn-home.back-arrow:hover {
            background-color: #00368a;
        }

        .arrow-icon {
            width: 18px;
            height: 18px;
            margin-right: 10px;
        }

        h2 {
            color: #0056b3;
            margin-bottom: 30px;
            text-align: center;
        }

        .info {
            margin-bottom: 20px;
            font-size: 18px;
            text-align: center;
        }

        .info strong {
            display: block;
            margin-bottom: 5px;
            color: #004080;
        }

        .social-icons {
            margin-top: 30px;
            text-align: center;
        }

        .social-icons img {
            width: 32px;
            height: 32px;
            margin: 0 10px;
            transition: transform 0.3s;
        }

        .social-icons img:hover {
            transform: scale(1.2);
        }
    </style>
</head>
<body>
    <div class="container">

        <!-- Botón igual a Misión/Visión -->
        <a href="Principal.jsp" class="btn-home back-arrow" target="_top">
            <svg class="arrow-icon" fill="white" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                <path d="M15 8a.5.5 0 0 1-.5.5H2.707l4.147 4.146a.5.5 0 0 1-.708.708l-5-5a.5.5 0 0 1 0-.708l5-5a.5.5 0 1 1 .708.708L2.707 7.5H14.5a.5.5 0 0 1 .5.5z"/>
            </svg>
            Volver al inicio
        </a>

        <h2>Contáctanos</h2>

        <div class="info">
            <strong>Teléfono</strong>
            <span>302 431 4213</span>
        </div>

        <div class="info">
            <strong>Correo electrónico</strong>
            <span>setorresa@udistrital.edu.co</span>
        </div>

        <div class="info">
            <strong>Ubicación</strong>
            <span>Bogotá, Colombia</span>
        </div>

        <div class="info">
            <strong>Síguenos</strong>
        </div>
        
        <div class="social-icons">
            <a href="#"><img src="img/facebook.png" alt="Facebook"></a>
            <a href="#"><img src="img/instagram.png" alt="Instagram"></a>
            <a href="#"><img src="img/x.png" alt="Twitter"></a>
            <a href="#"><img src="img/linkedin.png" alt="LinkedIn"></a>
        </div>

    </div>
</body>
</html>

