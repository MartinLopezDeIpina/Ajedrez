-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: localhost    Database: ajedrez
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `efectos`
--

DROP TABLE IF EXISTS `efectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `efectos` (
  `nombreEfecto` varchar(20) NOT NULL,
  `efecto` varchar(50) DEFAULT NULL,
  `tipoEfecto` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`nombreEfecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `efectos`
--

LOCK TABLES `efectos` WRITE;
/*!40000 ALTER TABLE `efectos` DISABLE KEYS */;
INSERT INTO `efectos` VALUES ('SELECCIONADO','0,0,-0.75,0','ColorAdjust');
/*!40000 ALTER TABLE `efectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagenesPiezas`
--

DROP TABLE IF EXISTS `imagenesPiezas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagenesPiezas` (
  `pathImagen` varchar(30) NOT NULL,
  `tipoPieza` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`pathImagen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagenesPiezas`
--

LOCK TABLES `imagenesPiezas` WRITE;
/*!40000 ALTER TABLE `imagenesPiezas` DISABLE KEYS */;
INSERT INTO `imagenesPiezas` VALUES ('imagenes/piezas/alfilB.png','alfilB'),('imagenes/piezas/alfilN.png','alfilN'),('imagenes/piezas/caballoB.png','caballoB'),('imagenes/piezas/caballoN.png','caballoN'),('imagenes/piezas/peonB.png','peonB'),('imagenes/piezas/peonN.png','peonN'),('imagenes/piezas/reinaB.png','reinaB'),('imagenes/piezas/reinaN.png','reinaN'),('imagenes/piezas/reyB.png','reyB'),('imagenes/piezas/reyN.png','reyN'),('imagenes/piezas/torreB.png','torreB'),('imagenes/piezas/torreN.png','torreN');
/*!40000 ALTER TABLE `imagenesPiezas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientoPartida`
--

DROP TABLE IF EXISTS `movimientoPartida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientoPartida` (
  `idPartida` int NOT NULL,
  `numMovimiento` int NOT NULL,
  `casillaOrigen` char(2) DEFAULT NULL,
  `casillaDestino` char(2) DEFAULT NULL,
  PRIMARY KEY (`idPartida`,`numMovimiento`),
  CONSTRAINT `movimientoPartida_ibfk_1` FOREIGN KEY (`idPartida`) REFERENCES `partida` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientoPartida`
--

LOCK TABLES `movimientoPartida` WRITE;
/*!40000 ALTER TABLE `movimientoPartida` DISABLE KEYS */;
INSERT INTO `movimientoPartida` VALUES (1,1,'h1','h2'),(1,2,'d6','d4'),(1,3,'f1','f3'),(1,4,'c7','g3'),(1,5,'a1','a2'),(1,6,'d7','d5'),(1,7,'g1','g2'),(1,8,'d5','c4'),(1,9,'f3','f4'),(1,10,'c4','e2'),(1,11,'c1','c2'),(1,12,'e2','d1'),(1,13,'b0','d1'),(1,14,'g3','e1'),(1,15,'d0','a3'),(1,16,'c6','c5'),(1,17,'e0','e1'),(1,18,'b7','a5'),(1,19,'a3','a4'),(1,20,'a5','c4'),(1,21,'a2','a3'),(1,22,'c4','e3'),(1,23,'d1','e3'),(1,24,'e6','e5'),(1,25,'c0','d1'),(1,26,'f7','b3'),(1,27,'d1','f3'),(1,28,'b3','c2'),(1,29,'f3','d5'),(1,30,'g7','f5'),(1,31,'e3','f5'),(1,32,'g6','f5'),(1,33,'d5','c4'),(1,34,'b6','b5'),(1,35,'a0','d0'),(1,36,'b5','a4'),(1,37,'d0','a0'),(1,38,'e7','a7'),(1,39,'a0','e0'),(1,40,'d4','d3'),(1,41,'c4','a2'),(1,42,'d3','d2'),(1,43,'e1','e2'),(1,44,'d2','d1'),(1,45,'f0','d2'),(1,46,'d1','e0'),(1,47,'g0','e1'),(1,48,'e0','h0'),(1,49,'a2','b3'),(1,50,'h0','h2'),(1,51,'b1','c2'),(1,52,'h2','g2'),(1,53,'e1','g2'),(1,54,'d7','d2'),(1,55,'e2','f1'),(1,56,'d2','g2'),(1,57,'b3','a2'),(1,58,'g2','c2'),(1,59,'a2','c4'),(1,60,'c2','c4'),(1,61,'f1','g0'),(1,62,'c4','f4'),(1,63,'g0','h0'),(1,64,'f4','f0'),(1,65,'h0','h1'),(1,66,'f0','f1'),(1,67,'h1','h0'),(1,68,'h6','h4'),(1,69,'h0','g0'),(1,70,'h4','h3'),(1,71,'g0','f1'),(1,72,'h3','h2'),(1,73,'f1','f0'),(1,74,'h2','h1'),(1,75,'f0','g1'),(1,76,'h1','h0'),(1,77,'g1','g2'),(1,78,'h7','h3'),(1,79,'g2','f1'),(1,80,'h0','f2'),(1,81,'f1','f2'),(1,82,'h3','f3'),(1,83,'f2','f3'),(1,84,'c5','c4'),(1,85,'f3','g2'),(1,86,'c4','c3'),(1,87,'g2','h1'),(1,88,'c3','c2'),(1,89,'h1','g2'),(1,90,'c2','c1'),(1,91,'g2','g3'),(1,92,'c1','c0'),(1,93,'g3','f2'),(1,94,'e5','e4'),(1,95,'f2','g3'),(1,96,'e4','e3'),(1,97,'g3','h3'),(1,98,'f5','f4'),(1,99,'h3','h4'),(1,100,'f4','f3'),(1,101,'h4','g4'),(1,102,'f3','f2'),(1,103,'g4','g3'),(1,104,'e3','e2'),(1,105,'g3','h3'),(1,106,'f2','f1'),(1,107,'h3','h2'),(1,108,'f1','f0'),(1,109,'h2','h1'),(1,110,'e2','e1'),(1,111,'h1','g2'),(1,112,'e1','e0'),(1,113,'g2','g3'),(1,114,'f6','f5'),(1,115,'g3','h4'),(1,116,'f5','f4'),(1,117,'h4','g5'),(1,118,'f4','f3'),(1,119,'g5','f5'),(1,120,'f3','f2'),(1,121,'f5','f4'),(1,122,'f2','f1'),(1,123,'f4','g5'),(1,124,'f0','e1'),(1,125,'g5','f6'),(1,126,'f1','f0'),(1,127,'f6','g7'),(1,128,'e1','e6'),(1,129,'g7','h7'),(1,130,'f0','f7');
/*!40000 ALTER TABLE `movimientoPartida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partida`
--

DROP TABLE IF EXISTS `partida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partida` (
  `identificador` int NOT NULL,
  `nombreJugadorA` varchar(20) NOT NULL,
  `nombreJugadorB` varchar(20) NOT NULL,
  `acabado` tinyint(1) DEFAULT '0',
  `esBotA` tinyint(1) DEFAULT '0',
  `esBotB` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`identificador`),
  KEY `claveNombre` (`nombreJugadorA`),
  KEY `claveNombreB` (`nombreJugadorB`),
  CONSTRAINT `claveNombre` FOREIGN KEY (`nombreJugadorA`) REFERENCES `usuario` (`nombre`),
  CONSTRAINT `claveNombreB` FOREIGN KEY (`nombreJugadorB`) REFERENCES `usuario` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partida`
--

LOCK TABLES `partida` WRITE;
/*!40000 ALTER TABLE `partida` DISABLE KEYS */;
INSERT INTO `partida` VALUES (1,'pepe2','pepe',1,1,0);
/*!40000 ALTER TABLE `partida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `nombre` varchar(20) NOT NULL,
  `contrasena` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('pepe','123'),('pepe2','123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-20 17:38:51
