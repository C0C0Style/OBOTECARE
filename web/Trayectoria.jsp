<%-- 
    Document   : Trayectoria
    Created on : 12/06/2025, 2:20:55 p. m.
    Author     : torre
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Trayectoria del Proyecto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 40px 20px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background-color: #fff;
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
        }

        p {
            text-align: justify;
            line-height: 1.6;
        }

        .timeline {
            margin-top: 40px;
            padding-left: 40px;
            position: relative;
            border-left: 3px solid #0056b3;
        }

        .event {
            margin-bottom: 30px;
            padding-left: 20px;
            position: relative;
        }

        .event::before {
            content: '';
            position: absolute;
            left: -29px;
            top: 6px;
            width: 14px;
            height: 14px;
            background-color: #0056b3;
            border-radius: 50%;
            z-index: 1;
        }

        .event h4 {
            margin: 0 0 5px;
            color: #004080;
            font-weight: bold;
            font-size: 16px;
        }

        .event p {
            margin: 0;
            font-size: 15px;
        }
    </style>
</head>
<body>
    <div class="container">

        <a href="Principal.jsp" class="btn-home back-arrow" target="_top">
            <svg class="arrow-icon" fill="white" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                <path d="M15 8a.5.5 0 0 1-.5.5H2.707l4.147 4.146a.5.5 0 0 1-.708.708l-5-5a.5.5 0 0 1 0-.708l5-5a.5.5 0 1 1 .708.708L2.707 7.5H14.5a.5.5 0 0 1 .5.5z"/>
            </svg>
            Volver al inicio
        </a>

        <h2>Trayectoria del Proyecto</h2>

        <p>
            <strong>Oboete Care</strong> es un proyecto que inicia en el primer semestre del 2025 en la Universidad Distrital como una propuesta innovadora para el acompañamiento digital de personas con síntomas tempranos de Alzheimer. 
            Desde su formulación, el objetivo ha sido construir una plataforma funcional y humanizada que integre historia clínica, recordatorios de medicamentos, y en el futuro, dispositivos de geolocalización para brindar mayor seguridad a los pacientes.
        </p>

        <div class="timeline">
            <div class="event">
                <h4>2025 - Inicio del proyecto</h4>
                <p>Formulación del problema, planteamiento de objetivos y recolección de requerimientos del sistema.</p>
            </div>

            <div class="event">
                <h4>2025 - Desarrollo del prototipo web</h4>
                <p>Creación de módulos iniciales como historia clínica, registro de pacientes y recordatorios de medicamentos.</p>
            </div>

            <div class="event">
                <h4>2025 - Pruebas de usabilidad</h4>
                <p>Validación del portal con usuarios simulados para detectar mejoras en la experiencia de usuario.</p>
            </div>

            <div class="event">
                <h4>2026 - Integración de manilla de geolocalización</h4>
                <p>Desarrollo de una manilla inteligente que permita rastreo y alertas en tiempo real para pacientes con Alzheimer.</p>
            </div>

            <div class="event">
                <h4>2026 - Proyección a entornos reales</h4>
                <p>Pruebas piloto con profesionales del área médica y cuidadores, buscando llevar la solución a un contexto clínico o comunitario.</p>
            </div>
        </div>

    </div>
</body>
</html>
