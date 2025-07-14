-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: sistema_gestionale_di_task_didattici_con_badge
-- ------------------------------------------------------
-- Server version	8.0.33
CREATE DATABASE IF NOT EXISTS sistema_gestionale_di_task_didattici_con_badge;
USE sistema_gestionale_di_task_didattici_con_badge;
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
-- Table structure for table `attivita`
--

DROP TABLE IF EXISTS `attivita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attivita` (
  `profilo_personale_studente_IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  `task_didattico_Titolo` varchar(50) NOT NULL,
  `Consegnato` tinyint NOT NULL,
  PRIMARY KEY (`profilo_personale_studente_IndirizzoEmailIstituzionale`,`task_didattico_Titolo`),
  KEY `fk_profilo_personale_has_task_didattico_task_didattico1_idx` (`task_didattico_Titolo`),
  KEY `fk_profilo_personale_has_task_didattico_profilo_personale1_idx` (`profilo_personale_studente_IndirizzoEmailIstituzionale`),
  CONSTRAINT `fk_profilo_personale_has_task_didattico_profilo_personale1` FOREIGN KEY (`profilo_personale_studente_IndirizzoEmailIstituzionale`) REFERENCES `profilo_personale` (`studente_IndirizzoEmailIstituzionale`),
  CONSTRAINT `fk_profilo_personale_has_task_didattico_task_didattico1` FOREIGN KEY (`task_didattico_Titolo`) REFERENCES `task_didattico` (`Titolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attivita`
--

LOCK TABLES `attivita` WRITE;
/*!40000 ALTER TABLE `attivita` DISABLE KEYS */;
INSERT INTO `attivita` VALUES ('A.Silenete@studente.it','Esercizio LinkedList',0),('A.Silenete@studente.it','Esercizio StringBuilder',1),('Fra.Davanz@studente.it','Esercizio Diodi',1),('Fra.Davanz@studente.it','Esercizio Diodi Zener',0),('Fra.Davanz@studente.it','Esercizio OP-AMP',1),('Fra.Davanz@studente.it','Teoria MOS',0),('Fra.dipinto@studente.it','Esercitazione Associazione',0),('Fra.dipinto@studente.it','Esercizio Composizione (stretta)',0),('Fra.dipinto@studente.it','Esercizio Gen-Spec',1),('Fra.dipinto@studente.it','Esercizio LinkedList',0),('Fra.dipinto@studente.it','Esercizio polimorfismo',0),('Fra.dipinto@studente.it','Esercizio StringBuilder',1),('Fra.dipinto@studente.it','Esercizio Tipi Generici',0),('H.Granger@studente.it','Esercizio Diodi',1),('H.Granger@studente.it','Esercizio Diodi Zener',1),('H.Granger@studente.it','Esercizio OP-AMP',0),('H.Granger@studente.it','Teoria MOS',1),('H.potter@studente.it','Esercizio Diodi',1),('H.potter@studente.it','Esercizio Diodi Zener',0),('H.potter@studente.it','Esercizio OP-AMP',0),('H.potter@studente.it','Teoria MOS',0),('I.Drago@studente.it','Esercizi seconda legge di Newton',1),('I.Drago@studente.it','Esercizio Massa Molla su piano inclinato',0),('I.Drago@studente.it','Studio del moto uniformemente accelerato',0),('I.Drago@studente.it','Studio Moto Parabolico',0),('KKO@studente.it','Esercizi seconda legge di Newton',1),('KKO@studente.it','Esercizio Massa Molla su piano inclinato',1),('KKO@studente.it','Studio del moto uniformemente accelerato',0),('KKO@studente.it','Studio Moto Parabolico',0),('M.Balotelli@studente.it','Esercizi seconda legge di Newton',0),('M.Balotelli@studente.it','Esercizio Massa Molla su piano inclinato',0),('M.Balotelli@studente.it','Studio del moto uniformemente accelerato',1),('M.Balotelli@studente.it','Studio Moto Parabolico',0),('M.damore@studente.it','Leggi di Maxwell',0),('M.damore@studente.it','Ottenre la costante c',0),('M.damore@studente.it','Teorema di Gauss per il campo magnetico',0),('M.damore@studente.it','Teoria Forza di Coulomb',1),('N.DelVerme@studente.it','Leggi di Maxwell',0),('N.DelVerme@studente.it','Ottenre la costante c',1),('N.DelVerme@studente.it','Teorema di Gauss per il campo magnetico',1),('N.DelVerme@studente.it','Teoria Forza di Coulomb',1),('R.Balboa@studente.it','Leggi di Maxwell',0),('R.Balboa@studente.it','Ottenre la costante c',1),('R.Balboa@studente.it','Teorema di Gauss per il campo magnetico',0),('R.Balboa@studente.it','Teoria Forza di Coulomb',0),('S.Catini@studente.it','Esercizio Integrali',0),('S.Catini@studente.it','Esercizio Serie',1),('S.Catini@studente.it','Esercizio Taylor',1),('S.Catini@studente.it','Studio di funzione',0),('S.esposito@studente.it','Esercizio Integrali',1),('S.esposito@studente.it','Esercizio Serie',0),('S.esposito@studente.it','Esercizio Taylor',0),('S.esposito@studente.it','Studio di funzione',1),('S.Piton@studente.it','Esercizio Integrali',0),('S.Piton@studente.it','Esercizio Serie',1),('S.Piton@studente.it','Esercizio Taylor',1),('S.Piton@studente.it','Studio di funzione',0),('V.dipinto@studente.it','Esercizio LinkedList',0),('V.dipinto@studente.it','Esercizio StringBuilder',1),('V.dipinto@studente.it','Esercizio Tipi Generici',0);
/*!40000 ALTER TABLE `attivita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `Nome` varchar(30) NOT NULL,
  `Descrizione` varchar(200) NOT NULL,
  `Immagine` tinyint NOT NULL,
  PRIMARY KEY (`Nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES ('Esperto Totale','Ormai stai viaggiando alla grande!! hai ottenuto ben 200 punti e 20 task completate per sbloccare questo task',1),('Macinatore di Task','Complimenti stai andando fortissimo con le task hai appena ottenuto il badge per aver completato 10 task',1),('Ottimo Inizio','Hai appena completato il badge dei 100 punti ottenuti complimenti. Ottimo inizio continua così',0);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classe_virtuale`
--

DROP TABLE IF EXISTS `classe_virtuale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classe_virtuale` (
  `CodiceUnivoco` varchar(10) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `docente_IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  PRIMARY KEY (`CodiceUnivoco`),
  KEY `fk_classe_virtuale_docente_idx` (`docente_IndirizzoEmailIstituzionale`),
  CONSTRAINT `fk_classe_virtuale_docente` FOREIGN KEY (`docente_IndirizzoEmailIstituzionale`) REFERENCES `docente` (`IndirizzoEmailIstituzionale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classe_virtuale`
--

LOCK TABLES `classe_virtuale` WRITE;
/*!40000 ALTER TABLE `classe_virtuale` DISABLE KEYS */;
INSERT INTO `classe_virtuale` VALUES ('0123456789','Analisi 1','Fra.dipinto@docente.it'),('1231231231','Programmazione 1','Fra.dipinto@docente.it'),('1414141414','Elettronica 1','Fra.dipinto@docente.it'),('4747474747','Fisica 1','S.Catini@docente.it'),('5353535353','Fisica 2','S.Catini@docente.it');
/*!40000 ALTER TABLE `classe_virtuale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
  `IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`IndirizzoEmailIstituzionale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES ('Fra.dipinto@docente.it','Francesco','Di Pinto','Ciao'),('S.Catini@docente.it','Simone','Catini','Ciao');
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profilo_personale`
--

DROP TABLE IF EXISTS `profilo_personale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profilo_personale` (
  `TotalePuntiOttenuti` int NOT NULL,
  `NumeroTaskSvolti` int NOT NULL,
  `studente_IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  PRIMARY KEY (`studente_IndirizzoEmailIstituzionale`),
  KEY `fk_profilo_personale_studente1_idx` (`studente_IndirizzoEmailIstituzionale`),
  CONSTRAINT `fk_profilo_personale_studente1` FOREIGN KEY (`studente_IndirizzoEmailIstituzionale`) REFERENCES `studente` (`IndirizzoEmailIstituzionale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profilo_personale`
--

LOCK TABLES `profilo_personale` WRITE;
/*!40000 ALTER TABLE `profilo_personale` DISABLE KEYS */;
INSERT INTO `profilo_personale` VALUES (140,1,'A.Silenete@studente.it'),(200,2,'Fra.Davanz@studente.it'),(5000,30,'Fra.dipinto@studente.it'),(620,3,'H.Granger@studente.it'),(150,1,'H.potter@studente.it'),(100,1,'I.Drago@studente.it'),(600,2,'KKO@studente.it'),(30,1,'M.Balotelli@studente.it'),(120,1,'M.damore@studente.it'),(100,1,'N.DelVerme@studente.it'),(50,1,'R.Balboa@studente.it'),(250,2,'S.Catini@studente.it'),(350,2,'S.esposito@studente.it'),(300,2,'S.Piton@studente.it'),(140,1,'V.dipinto@studente.it');
/*!40000 ALTER TABLE `profilo_personale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `riconoscimento`
--

DROP TABLE IF EXISTS `riconoscimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `riconoscimento` (
  `profilo_personale_studente_IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  `badge_Nome` varchar(30) NOT NULL,
  `DataDiOttenimento` date DEFAULT NULL,
  PRIMARY KEY (`profilo_personale_studente_IndirizzoEmailIstituzionale`,`badge_Nome`),
  KEY `fk_profilo_personale_has_badge_badge1_idx` (`badge_Nome`),
  KEY `fk_profilo_personale_has_badge_profilo_personale1_idx` (`profilo_personale_studente_IndirizzoEmailIstituzionale`),
  CONSTRAINT `fk_profilo_personale_has_badge_badge1` FOREIGN KEY (`badge_Nome`) REFERENCES `badge` (`Nome`),
  CONSTRAINT `fk_profilo_personale_has_badge_profilo_personale1` FOREIGN KEY (`profilo_personale_studente_IndirizzoEmailIstituzionale`) REFERENCES `profilo_personale` (`studente_IndirizzoEmailIstituzionale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `riconoscimento`
--

LOCK TABLES `riconoscimento` WRITE;
/*!40000 ALTER TABLE `riconoscimento` DISABLE KEYS */;
INSERT INTO `riconoscimento` VALUES ('A.Silenete@studente.it','Ottimo Inizio','2025-06-28'),('Fra.Davanz@studente.it','Ottimo Inizio','2025-06-28'),('Fra.dipinto@studente.it','Esperto Totale','2025-06-26'),('Fra.dipinto@studente.it','Macinatore di Task','2025-06-26'),('Fra.dipinto@studente.it','Ottimo Inizio','2025-06-26'),('H.Granger@studente.it','Ottimo Inizio','2025-06-28'),('H.potter@studente.it','Ottimo Inizio','2025-06-28'),('I.Drago@studente.it','Ottimo Inizio','2025-06-28'),('KKO@studente.it','Ottimo Inizio','2025-06-28'),('M.damore@studente.it','Ottimo Inizio','2025-06-28'),('N.DelVerme@studente.it','Ottimo Inizio','2025-06-28'),('S.Catini@studente.it','Ottimo Inizio','2025-06-28'),('S.esposito@studente.it','Ottimo Inizio','2025-06-28'),('S.Piton@studente.it','Ottimo Inizio','2025-06-28'),('V.dipinto@studente.it','Ottimo Inizio','2025-06-28');
/*!40000 ALTER TABLE `riconoscimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studente`
--

DROP TABLE IF EXISTS `studente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studente` (
  `IndirizzoEmailIstituzionale` varchar(65) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Password` varchar(30) DEFAULT NULL,
  `classe_virtuale_CodiceUnivoco` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`IndirizzoEmailIstituzionale`),
  KEY `fk_studente_classe_virtuale1_idx` (`classe_virtuale_CodiceUnivoco`),
  CONSTRAINT `fk_studente_classe_virtuale1` FOREIGN KEY (`classe_virtuale_CodiceUnivoco`) REFERENCES `classe_virtuale` (`CodiceUnivoco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studente`
--

LOCK TABLES `studente` WRITE;
/*!40000 ALTER TABLE `studente` DISABLE KEYS */;
INSERT INTO `studente` VALUES ('A.Silenete@studente.it','Albus','Silente','Preside','1231231231'),('Fra.Davanz@studente.it','Francesco','D\'Avanzo','Ciao','1414141414'),('Fra.dipinto@studente.it','Francesco','Di Pinto','Ciao','1231231231'),('H.Granger@studente.it','Hermione','Granger','Grifondoro','1414141414'),('H.potter@studente.it','Harry','Potter','Grifondoro','1414141414'),('I.Drago@studente.it','Ivan','Drago','Pugile','4747474747'),('KKO@studente.it','Francesco','Cunzolo','Ciao','4747474747'),('M.Balotelli@studente.it','Mario','Balotelli','Calciatore','4747474747'),('M.damore@studente.it','Marco','D\'Amore','Ciro','5353535353'),('N.DelVerme@studente.it','Nicola','Del Verme','Ciao','5353535353'),('R.Balboa@studente.it','Rocky','Balboa','Campione','5353535353'),('S.Catini@studente.it','Simone','Catini','Ciao','0123456789'),('S.esposito@studente.it','Salvatore','Esposito','Gennaro','0123456789'),('S.Piton@studente.it','Severus','Piton','Professore','0123456789'),('V.dipinto@studente.it','Vincenzo','Di Pinto','Hello','1231231231');
/*!40000 ALTER TABLE `studente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_didattico`
--

DROP TABLE IF EXISTS `task_didattico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_didattico` (
  `Titolo` varchar(50) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  `DataDiScadenza` date NOT NULL,
  `NumeroMassimoDiPuntiAssegnabili` int NOT NULL,
  `classe_virtuale_CodiceUnivoco` varchar(10) NOT NULL,
  PRIMARY KEY (`Titolo`),
  KEY `fk_task_didattico_classe_virtuale1_idx` (`classe_virtuale_CodiceUnivoco`),
  CONSTRAINT `fk_task_didattico_classe_virtuale1` FOREIGN KEY (`classe_virtuale_CodiceUnivoco`) REFERENCES `classe_virtuale` (`CodiceUnivoco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_didattico`
--

LOCK TABLES `task_didattico` WRITE;
/*!40000 ALTER TABLE `task_didattico` DISABLE KEYS */;
INSERT INTO `task_didattico` VALUES ('Esercitazione Associazione','Scrivi almeno due classi di cui rappresenta il contenimento lasco verso l\'altra','2025-07-26',300,'1231231231'),('Esercizi seconda legge di Newton','Descrivere la seconda legge di Newton la sua caratteristica e sviluppare anche in forma differenziale','2025-07-09',200,'4747474747'),('Esercizio Composizione (stretta)','Scrivere una serie di classi che rappresentino almeno un contenimento stretto','2025-07-29',100,'1231231231'),('Esercizio Diodi','Studiare il circuito che presenta un diodo, è direttamente o inversamente polarizzato?','2025-09-10',200,'1414141414'),('Esercizio Diodi Zener','Studiare il circuito che presenta un diodo Zener che differenza c\'è con un diodo normale?  qual è la tensione di breakdown?','2025-09-11',210,'1414141414'),('Esercizio Gen-Spec','Scrivere una serie di classi che rappresentino almeno una relazione di generalizzazione specializzazione','2025-07-30',150,'1231231231'),('Esercizio Integrali','Sviluppare il calco dell\' integrale  1/x^4','2025-07-28',210,'0123456789'),('Esercizio LinkedList','Scrivere tutti i metodi classici di una LinkedList e poi usare l\'api offerta da Java per confrontare le differenze','2025-07-21',160,'1231231231'),('Esercizio Massa Molla su piano inclinato','Sviluppare un esercizio simil esame, in cui il sistema in esame è una Sistma di Massa collegata ad una molla su un piano inclinato di angolo theta = 30 gradi','2025-09-20',500,'4747474747'),('Esercizio OP-AMP','Disegnare un circuito con un Amplificatore operazionale e descrivere se questo è invertente o non invertente.','2025-09-29',300,'1414141414'),('Esercizio polimorfismo','Applicare il concetto di polimorfismo ad almeno un paio di classi facendo l\'override di qualche metodo anche il semplice to string o equals','2025-07-30',200,'1231231231'),('Esercizio Serie','Scrivere una serie geometrica e descriverne il comportamento','2025-07-15',200,'0123456789'),('Esercizio StringBuilder','Scrivere delle classi Java che denotano la differenza tra String e StringBuilder','2025-07-18',140,'1231231231'),('Esercizio Taylor','Sviluppare in serie di Taylor sin(x)','2025-07-15',200,'0123456789'),('Esercizio Tipi Generici','Sviluppare delle classi java che sfruttino la possbilità di creare metodi con i tipi generici notazione (<>)','2025-07-24',400,'1231231231'),('Leggi di Maxwell','Definire quali sono le leggi di maxwell in forma integrale, cos\'è la corrente di spostamento?','2025-07-03',300,'5353535353'),('Ottenre la costante c','Che cos\'è la costante C? com\'è definita? ottenere l\'espressione.','2025-07-30',500,'5353535353'),('Studio del moto uniformemente accelerato','Scrivere le equazioni che descrivono il moto uniformemente accelerato','2025-07-07',100,'4747474747'),('Studio di funzione','Studiare la funzione e^x +5x^2','2025-07-30',150,'0123456789'),('Studio Moto Parabolico','Completare degli esercizi a scelta dal libro sul moto parabolico','2025-07-08',120,'4747474747'),('Teorema di Gauss per il campo magnetico','Descrivere cosa dice il teorema di Gauss per il campo magnetico, com\'è definito e in che leggi è raggruppato.','2025-07-02',140,'5353535353'),('Teoria Forza di Coulomb','Descrivere la formula che rappresenta la Forza di Coulomb: le grandezze in gioco, con le relative unità di misura.','2025-07-01',120,'5353535353'),('Teoria MOS','Quante sono le regioni di operazione del MOS? come si chiamano? che caratteristiche hanno?','2025-09-15',210,'1414141414');
/*!40000 ALTER TABLE `task_didattico` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-13 17:05:00
