<%-- 
    Document   : EquipoTrabajo
    Created on : 12/06/2025, 2:24:41 p. m.
    Author     : torre
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Equipo de Trabajo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 40px 20px;
            overflow-x: hidden;
        }

        .container {
            max-width: 1100px;
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

        .slider-container {
            position: relative;
            max-width: 100%;
            margin: auto;
            overflow: hidden;
        }

        .slider {
            display: flex;
            gap: 20px;
            padding: 20px;
            scroll-behavior: smooth;
            overflow-x: auto;
            scrollbar-width: none; /* Firefox */
        }

        .slider::-webkit-scrollbar {
            display: none;
        }

        .profile {
            flex: 0 0 280px;
            background-color: white;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }

        .profile img {
            width: 100%;
            height: 250px;
            object-fit: cover;
            border-radius: 10px;
            margin-bottom: 15px;
        }

        .profile h3 {
            margin: 10px 0 5px;
            font-size: 18px;
            color: #003366;
        }

        .profile p {
            font-size: 14px;
            margin: 0;
            color: #555;
        }

        .arrow-btn {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            font-size: 30px;
            background: none;
            border: none;
            cursor: pointer;
            color: #0056b3;
            padding: 10px;
            z-index: 1;
        }

        .arrow-left {
            left: 0;
        }

        .arrow-right {
            right: 0;
        }

        @media (max-width: 768px) {
            .profile {
                flex: 0 0 90%;
            }
        }
    </style>
</head>
<body>
    <div class="container">

        <!-- Botón igual al de Misión y Visión -->
        <a href="Principal.jsp" class="btn-home back-arrow" target="_top">
            <svg class="arrow-icon" fill="white" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                <path d="M15 8a.5.5 0 0 1-.5.5H2.707l4.147 4.146a.5.5 0 0 1-.708.708l-5-5a.5.5 0 0 1 0-.708l5-5a.5.5 0 1 1 .708.708L2.707 7.5H14.5a.5.5 0 0 1 .5.5z"/>
            </svg>
            Volver al inicio
        </a>

        <h2>Equipo de Trabajo</h2>

        <div class="slider-container">
            <button class="arrow-btn arrow-left" onclick="scrollSlider(-1)">&#10094;</button>

            <div class="slider" id="slider">
                <div class="profile">
                    <img src="img/dev1.jpg" alt="Desarrollador">
                    <h3>Juan Pérez</h3>
                    <p>Ingeniero de Sistemas</p>
                    <p>Especialista en desarrollo web y backend</p>
                </div>
                <div class="profile">
                    <img src="img/ux1.jpg" alt="Diseñadora">
                    <h3>María Gómez</h3>
                    <p>Diseñadora UX/UI</p>
                    <p>Accesibilidad y diseño centrado en el usuario</p>
                </div>
                <div class="profile">
                    <img src="img/med1.jpg" alt="Médico">
                    <h3>Dr. Andrés Ruiz</h3>
                    <p>Médico Geriatra</p>
                    <p>Enfocado en enfermedades neurodegenerativas</p>
                </div>
                <div class="profile">
                    <img src="img/psico.jpg" alt="Psicóloga">
                    <h3>Dra. Laura Herrera</h3>
                    <p>Psicóloga Clínica</p>
                    <p>Especialista en trastornos cognitivos</p>
                </div>
                <div class="profile">
                    <img src="img/voluntario1.jpg" alt="Voluntario">
                    <h3>Camilo Ortega</h3>
                    <p>Voluntario</p>
                    <p>Apoyo emocional y técnico a usuarios</p>
                </div>
            </div>

            <button class="arrow-btn arrow-right" onclick="scrollSlider(1)">&#10095;</button>
        </div>
    </div>

    <script>
        function scrollSlider(direction) {
            const slider = document.getElementById('slider');
            const scrollAmount = 300;
            slider.scrollLeft += direction * scrollAmount;
        }
    </script>
</body>
</html>
