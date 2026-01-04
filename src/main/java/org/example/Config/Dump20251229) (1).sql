CREATE DATABASE  IF NOT EXISTS `qlhssv` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlhssv`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qlhssv
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `username` varchar(30) NOT NULL,
  `password` varchar(45) NOT NULL,
  `fullname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('anhdth','admin','Đỗ Thái Hải Anh'),('anhnv','admin','Nguyễn Văn Anh'),('baodg','admin','Đinh Gia Bảo'),('ducpa','admin','Phạm Anh Đức'),('truongpm','admin','Phùng Minh Trường');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_id` varchar(10) NOT NULL,
  `class_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `major_id` varchar(10) NOT NULL,
  `cohort` int NOT NULL,
  PRIMARY KEY (`class_id`),
  UNIQUE KEY `class_name_UNIQUE` (`class_name`),
  KEY `FK_Major_idx` (`major_id`),
  CONSTRAINT `FK_Major` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES ('HT21','74DCHT21','HT',74),('LG21','74DCLG21','LG',74),('RM1','Rau má 1','RM',1),('RM2','Rau má 2','RM',1),('RM3','Rau má 3','RM',1),('RM4','Rau má 4','RM',1);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculties`
--

DROP TABLE IF EXISTS `faculties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculties` (
  `facu_id` varchar(10) NOT NULL,
  `facu_name` varchar(50) NOT NULL,
  PRIMARY KEY (`facu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculties`
--

LOCK TABLES `faculties` WRITE;
/*!40000 ALTER TABLE `faculties` DISABLE KEYS */;
INSERT INTO `faculties` VALUES ('36','Thanh Hóa'),('CKDL','Cơ khí động lực'),('CNTT','Công nghệ thông tin'),('CT','Công trình'),('KHUD','Khoa học ứng dụng'),('KTVT','Kinh tế vận tải'),('LCT','Luật - Chính trị'),('QT','Quản trị');
/*!40000 ALTER TABLE `faculties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kyluat`
--

DROP TABLE IF EXISTS `kyluat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kyluat` (
  `idkyluat` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `hinhThuc` varchar(30) NOT NULL,
  `soQuyetDinh` varchar(50) DEFAULT NULL,
  `kyluat_date` date NOT NULL,
  `ngayKetThuc` date DEFAULT NULL,
  `lyDo` text NOT NULL,
  PRIMARY KEY (`idkyluat`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `kyluat_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kyluat`
--

LOCK TABLES `kyluat` WRITE;
/*!40000 ALTER TABLE `kyluat` DISABLE KEYS */;
INSERT INTO `kyluat` VALUES (1,'74DCHT22041','Khiển trách','124/23','2025-12-28','2025-12-28','Đẹp trai'),(3,'74DCHT22041','Khiển trách','124/24','2025-12-28','2025-12-28','Vua đẹp trai');
/*!40000 ALTER TABLE `kyluat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major` (
  `major_id` varchar(10) NOT NULL,
  `major_name` varchar(100) NOT NULL,
  `facu_id` varchar(10) NOT NULL,
  PRIMARY KEY (`major_id`),
  KEY `FK_facu_id_idx` (`facu_id`),
  CONSTRAINT `FK_facu_id` FOREIGN KEY (`facu_id`) REFERENCES `faculties` (`facu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES ('DT','Điện tử viễn thông','CNTT'),('EN','Ngôn ngữ Anh','KHUD'),('HT','Hệ thống thông tin','CNTT'),('LG','Logistics và quản lý chuỗi cung ứng','KTVT'),('QM','Quản trị Marketing','QT'),('RM','Rau má học','36');
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reward`
--

DROP TABLE IF EXISTS `reward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reward` (
  `reward_id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `reward_date` date NOT NULL,
  `reward_note` text NOT NULL,
  `reward_quyetdinh` varchar(20) NOT NULL,
  PRIMARY KEY (`reward_id`),
  UNIQUE KEY `reward_quyetdinh_UNIQUE` (`reward_quyetdinh`),
  KEY `FK_Stu_Reward_idx` (`student_id`),
  CONSTRAINT `FK_Stu_Reward` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reward`
--

LOCK TABLES `reward` WRITE;
/*!40000 ALTER TABLE `reward` DISABLE KEYS */;
INSERT INTO `reward` VALUES (2,'74DCHT22041','2025-12-28','Đẹp trai','129');
/*!40000 ALTER TABLE `reward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` varchar(20) NOT NULL,
  `student_lastName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `student_firstName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `hometown` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `id_no` varchar(12) NOT NULL,
  `class_id` varchar(10) NOT NULL,
  `status` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_no_UNIQUE` (`id_no`),
  KEY `FK_ClassID_idx` (`class_id`),
  CONSTRAINT `FK_ClassID` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('1211','12122','21212','2025-12-18','Nữ','12122','3123123','3123123','2231231223','RM1','Đang theo học'),('12121','121212','1212','2025-12-11','Nam','212','12121','1212','1212','RM1','Bị đình chỉ'),('453455','453453','3453','2025-12-10','Nam','45345','34534','45345','534534','RM2','Buộc thôi học'),('74DCHT22041','Phạm Anh','Đức','2005-01-30','Nam','Thái Bình','0962819282','duc74dcht22041@st.utt.edu.vn','034205006846','HT21','Đang theo học');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-29  3:04:48
