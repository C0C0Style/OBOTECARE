<%-- 
    Document   : visionMision
    Created on : 12/06/2025, 1:43:00 p. m.
    Author     : torre
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Visión y Misión - Oboete Care</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f8f9fa;
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: auto;
        }
        h2 {
            color: #0056b3;
            margin-top: 30px;
        }
        p {
            text-align: justify;
            line-height: 1.6;
        }
        .section {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-bottom: 40px;
        }
        .section img {
            width: 200px;
            height: auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .btn-home {
            display: inline-block;
            text-decoration: none;
            background-color: #0056b3;
            color: white;
            padding: 10px 20px;
            border-radius: 30px;
            transition: background-color 0.3s ease;
        }
        .btn-home:hover {
            background-color: #004494;
        }
        .back-arrow {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 30px;
        }
        .back-arrow svg {
            width: 20px;
            height: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="Principal.jsp" class="btn-home back-arrow" target="_top">
            <svg fill="white" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                <path d="M15 8a.5.5 0 0 1-.5.5H2.707l4.147 4.146a.5.5 0 0 1-.708.708l-5-5a.5.5 0 0 1 0-.708l5-5a.5.5 0 1 1 .708.708L2.707 7.5H14.5a.5.5 0 0 1 .5.5z"/>
            </svg>
            Volver al inicio
        </a>

        <div class="section">
            <img src="img/vision.png" alt="Visión">
            <div>
                <h2>Visión</h2>
                <p>
                    Convertirse en una plataforma de referencia en el acompañamiento digital de personas con síntomas tempranos de Alzheimer,
                    integrando tecnología accesible, respaldo clínico y un enfoque humano, con el fin de mejorar la calidad de vida de los pacientes
                    y sus cuidadores en toda la comunidad hispanohablante.
                </p>
            </div>
        </div>

        <div class="section">
            <img src="img/mision.png" alt="Misión">
            <div>
                <h2>Misión</h2>
                <p>
                    Brindar apoyo tecnológico y emocional a personas con deterioro cognitivo leve o en etapa inicial de Alzheimer, facilitando el
                    registro de síntomas, la gestión de recordatorios y el acceso a información educativa, mediante un portal web confiable y fácil de
                    usar que promueva el cuidado, la autonomía y la conciencia sobre la salud mental.
                </p>
            </div>
        </div>
    </div>
</body>
</html>
