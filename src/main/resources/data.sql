-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 28, 2018 at 02:33 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `helpmycity`
--

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`categoryid`, `category_description`, `category_image`, `category_title`) VALUES
(1, 'Cumul de déchets des passants - ordures accumulées dans la rue - absence de conteneur poubelle…', 'http://www.plan126.com/ios_icons/4.png', 'Pollution'),
(2, 'Fuite d’eau – coupures d’eau fréquentes – eau sale au robinet... ', 'http://www.plan126.com/ios_icons/10.png', 'Problèmes d\'eau'),
(3, 'Pas de lumière publique le soir – coupures fréquentes d’électricité – câbles d’électricité non couverts ou défectueux…', 'http://www.plan126.com/ios_icons/11.png', 'Problèmes d\'électricité');

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_AGENT'),
(3, 'ROLE_ADMIN');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;