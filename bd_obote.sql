-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-06-2025 a las 19:24:13
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_obote`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acudiente`
--

CREATE TABLE `acudiente` (
  `id` int(11) NOT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `documento` varchar(20) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `acudiente`
--

INSERT INTO `acudiente` (`id`, `nombres`, `apellidos`, `documento`, `telefono`, `correo`) VALUES
(2, 'Carmensa', 'Torres', '1032', '777823', '11@coco.com'),
(9, 'Santiagos', 'Torres', '10324', '988252459', '213@a'),
(10, 'Estefanya', 'Johana', '103245', '3024314213', 'estefa@gmail.com'),
(12, 'Nohemy', 'Quinallas', '18732', '777823', '11@coco.com'),
(13, 'Pepito', 'Rodriguez', '444', '666', 'pepas@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `id` int(11) NOT NULL,
  `Nombres` varchar(100) NOT NULL,
  `Apellidos` varchar(100) NOT NULL,
  `Diagnostico` varchar(150) DEFAULT NULL,
  `NumeroDocumento` varchar(20) DEFAULT NULL,
  `FechaNacimiento` varchar(20) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Correo` varchar(100) DEFAULT NULL,
  `Historial` varchar(255) DEFAULT NULL,
  `Estado` varchar(2) DEFAULT NULL,
  `idAcudiente` int(11) DEFAULT NULL,
  `parentesco` varchar(50) DEFAULT NULL,
  `telefonoContacto` varchar(20) DEFAULT NULL,
  `IdProfesional` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`id`, `Nombres`, `Apellidos`, `Diagnostico`, `NumeroDocumento`, `FechaNacimiento`, `Direccion`, `Telefono`, `Correo`, `Historial`, `Estado`, `idAcudiente`, `parentesco`, `telefonoContacto`, `IdProfesional`) VALUES
(14, 'gaitan', 'Urrego', 'U071', '123', '0001-01-01', 'Calle 71 Sur No 81-79', '987', 'setorresa@unal.edu.co', 'archivos/123.pdf', '1', 2, 'Madre', '7778', 2),
(17, 'Estefanya', 'Torres', 'G30.0', '1988252459', '2025-06-09', 'medellin', '3024314213', 'estefa@gmail.com', 'archivos/1988252459', '1', 10, 'Mi novia', '3024314213', 6),
(21, 'Ingrid Rubio', 'Castellon', 'G30.0', '103069613712', '2025-06-17', 'Bogota', '988252459', 'asda@a', 'archivos/103069613712', '1', 10, 'Tía', '3024314213', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesional`
--

CREATE TABLE `profesional` (
  `IdEmpleado` int(11) NOT NULL,
  `Dni` varchar(20) NOT NULL,
  `Nombres` varchar(100) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Estado` varchar(10) DEFAULT NULL,
  `User` varchar(50) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `IdUsuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesional`
--

INSERT INTO `profesional` (`IdEmpleado`, `Dni`, `Nombres`, `Telefono`, `Estado`, `User`, `contraseña`, `IdUsuario`) VALUES
(2, '2', 'Estefanya Bohorquez', '98825254', '2', 'Marquito213213123', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=', NULL),
(6, '3', 'Santiagos Martinez', '9882524592', '2', 'Martin', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=', NULL),
(7, '4', 'Daniel Caballero', '988252', '1', 'Marquito432', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=', NULL),
(11, '5', 'Doctor Jaus', '988252', '1', 'Foreman', 'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recordatorios`
--

CREATE TABLE `recordatorios` (
  `idRecordatorio` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `fechaRecordatorio` datetime NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `estado` varchar(50) DEFAULT 'Pendiente',
  `fechaCreacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `idProfesionalCreador` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recordatorios`
--

INSERT INTO `recordatorios` (`idRecordatorio`, `idPaciente`, `fechaRecordatorio`, `descripcion`, `estado`, `fechaCreacion`, `idProfesionalCreador`) VALUES
(4, 14, '2025-06-19 22:29:00', 'Como estamosgente', 'Cancelado', '2025-06-30 00:29:15', NULL),
(5, 14, '2025-06-25 22:33:00', 'Otro recordatorio', 'Completado', '2025-06-30 00:30:55', NULL),
(8, 21, '2025-06-16 09:54:00', 'Recordatorio que no se puede borrar', 'Pendiente', '2025-06-30 00:53:10', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `IdUsuario` int(11) NOT NULL,
  `Dni` varchar(20) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Rol` varchar(50) NOT NULL,
  `Correo` varchar(100) NOT NULL,
  `Estado` varchar(10) NOT NULL,
  `User` varchar(50) NOT NULL,
  `Contraseña` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`IdUsuario`, `Dni`, `Nombre`, `Rol`, `Correo`, `Estado`, `User`, `Contraseña`) VALUES
(3, '99050801809123', 'Cocoss', '2', 'setorresa@unal.edu.co', '1', 'setorresa', '123');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `acudiente`
--
ALTER TABLE `acudiente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `documento` (`documento`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `NumeroDocumento` (`NumeroDocumento`),
  ADD KEY `fk_paciente_acudiente` (`idAcudiente`),
  ADD KEY `fk_paciente_profesional` (`IdProfesional`);

--
-- Indices de la tabla `profesional`
--
ALTER TABLE `profesional`
  ADD PRIMARY KEY (`IdEmpleado`),
  ADD UNIQUE KEY `user_unique` (`User`),
  ADD KEY `fk_profesional_usuario` (`IdUsuario`);

--
-- Indices de la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  ADD PRIMARY KEY (`idRecordatorio`),
  ADD KEY `idPaciente` (`idPaciente`),
  ADD KEY `idProfesionalCreador` (`idProfesionalCreador`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`IdUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `acudiente`
--
ALTER TABLE `acudiente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `profesional`
--
ALTER TABLE `profesional`
  MODIFY `IdEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  MODIFY `idRecordatorio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `IdUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_paciente_acudiente` FOREIGN KEY (`idAcudiente`) REFERENCES `acudiente` (`id`),
  ADD CONSTRAINT `fk_paciente_profesional` FOREIGN KEY (`IdProfesional`) REFERENCES `profesional` (`IdEmpleado`);

--
-- Filtros para la tabla `profesional`
--
ALTER TABLE `profesional`
  ADD CONSTRAINT `fk_profesional_usuario` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`IdUsuario`);

--
-- Filtros para la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  ADD CONSTRAINT `recordatorios_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`id`),
  ADD CONSTRAINT `recordatorios_ibfk_2` FOREIGN KEY (`idProfesionalCreador`) REFERENCES `profesional` (`IdEmpleado`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
