-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2025 at 06:27 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bd_obote`
--

-- --------------------------------------------------------

--
-- Table structure for table `acudiente`
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
-- Dumping data for table `acudiente`
--

INSERT INTO `acudiente` (`id`, `nombres`, `apellidos`, `documento`, `telefono`, `correo`) VALUES
(2, 'Carmens', 'Torress', '1032', '7778', '11@coco.com');

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `IdCliente` int(11) UNSIGNED NOT NULL,
  `Dni` varchar(8) DEFAULT NULL,
  `Nombres` varchar(244) DEFAULT NULL,
  `Direccion` varchar(244) DEFAULT NULL,
  `Estado` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`IdCliente`, `Dni`, `Nombres`, `Direccion`, `Estado`) VALUES
(17, '2', 'Juan Guerrero Solis COC', 'Los Alamos', '2'),
(18, '1', 'Maria Rosas Villanueva', 'Los Laureles 234', '1'),
(19, '3', 'Andres de Santa Cruz', 'Av. La Frontera 347', '1'),
(20, '4', 'Andres Mendoza', 'Chosica, Lurigancho', '1');

-- --------------------------------------------------------

--
-- Table structure for table `paciente`
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
  `telefonoContacto` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paciente`
--

INSERT INTO `paciente` (`id`, `Nombres`, `Apellidos`, `Diagnostico`, `NumeroDocumento`, `FechaNacimiento`, `Direccion`, `Telefono`, `Correo`, `Historial`, `Estado`, `idAcudiente`, `parentesco`, `telefonoContacto`) VALUES
(12, 'Santiago', 'Torres', 'G30.0', '1030696137', '1999-05-08', 'Calle 71 Sur No 81-79', '3024314213', 'setorresa@unal.edu.co', 'archivos/1030696137.png', '1', 2, 'Madre', '7778'),
(13, '1', '1', '1', '1', '0001-01-01', '1', '1', '1@coco.com', 'archivos/1', '1', NULL, NULL, NULL),
(14, 'gaitana', 'gay', 'U071', '123', '0001-01-01', 'Calle 71 Sur No 81-79', '987', 'setorresa@unal.edu.co', 'archivos/123.pdf', '1', 2, 'Madre', '7778');

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
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
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`IdUsuario`, `Dni`, `Nombre`, `Rol`, `Correo`, `Estado`, `User`, `Contraseña`) VALUES
(2, '0', 'S', '2', '2@coco.com', '2', 'cocos', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(3, '99050801809', 'Coco', '2', 'setorresa@unal.edu.co', '1', 'setorresa', 'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acudiente`
--
ALTER TABLE `acudiente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `documento` (`documento`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IdCliente`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `NumeroDocumento` (`NumeroDocumento`),
  ADD KEY `fk_paciente_acudiente` (`idAcudiente`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`IdUsuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acudiente`
--
ALTER TABLE `acudiente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `IdCliente` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `paciente`
--
ALTER TABLE `paciente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `IdUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_paciente_acudiente` FOREIGN KEY (`idAcudiente`) REFERENCES `acudiente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
