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
  `class_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `major_id` varchar(10) NOT NULL,
  `teacher_id` varchar(10) NOT NULL,
  `cohort_id` int NOT NULL,
  `student_max` int NOT NULL,
  `student_current` int DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `FK_CL_TC_idx` (`teacher_id`),
  KEY `FK_CL_MJ_idx` (`major_id`),
  KEY `FL_CL_CH_idx` (`cohort_id`),
  CONSTRAINT `FK_CL_MJ` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`),
  CONSTRAINT `FK_CL_TC` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  CONSTRAINT `FL_CL_CH` FOREIGN KEY (`cohort_id`) REFERENCES `cohort` (`cohort_id`),
  CONSTRAINT `chk_student_current` CHECK ((`student_current` <= `student_max`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES ('74HT01','Hệ thống thông tin 01 - K74','HT','CNTT2201',7,12,0),('74QM01','Quản trị Marketing 01 - K74','QM','QT0089',7,5,4),('75HT01','Hệ thống thông tin 01 - K75','HT','CNTT2201',5,10,1),('75KN01','Kiến trúc nội thất 01 - K75','KN','CT0016',5,10,3),('75LA01','Luật 01 - K75','LA','LCT0035',5,5,1),('75LG01','Logistics và quản lý chuỗi cung ứng 01 - K75','LG','KTVT0036',5,36,0),('76DT01','Điện tử viễn thông 01 - K76','DT','CNTT0012',4,10,0),('76EN01','Ngôn ngữ Anh 01 - K76','EN','KHUD0021',4,5,2);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cohort`
--

DROP TABLE IF EXISTS `cohort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cohort` (
  `cohort_id` int NOT NULL AUTO_INCREMENT,
  `cohort_name` varchar(20) NOT NULL,
  `cohort_start_year` int NOT NULL,
  `cohort_end_year` int NOT NULL,
  PRIMARY KEY (`cohort_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cohort`
--

LOCK TABLES `cohort` WRITE;
/*!40000 ALTER TABLE `cohort` DISABLE KEYS */;
INSERT INTO `cohort` VALUES (4,'K76',2025,2029),(5,'K75',2024,2028),(7,'K74',2023,2027);
/*!40000 ALTER TABLE `cohort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculties`
--

DROP TABLE IF EXISTS `faculties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculties` (
  `facu_id` varchar(10) NOT NULL,
  `facu_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`facu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculties`
--

LOCK TABLES `faculties` WRITE;
/*!40000 ALTER TABLE `faculties` DISABLE KEYS */;
INSERT INTO `faculties` VALUES ('CKDL','Cơ khí động lực'),('CNTT','Công nghệ thông tin'),('CT','Công trình'),('KHUD','Khoa học ứng dụng'),('KTVT','Kinh tế vận tải'),('LCT','Luật - Chính trị'),('QT','Quản trị');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kyluat`
--

LOCK TABLES `kyluat` WRITE;
/*!40000 ALTER TABLE `kyluat` DISABLE KEYS */;
INSERT INTO `kyluat` VALUES (1,'K74QM0003','Khiển trách','1231','2026-01-06','2026-01-13','Ý thức tham gia giao thông yếu');
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
INSERT INTO `major` VALUES ('DT','Điện tử viễn thông','CNTT'),('EN','Ngôn ngữ Anh','KHUD'),('HT','Hệ thống thông tin','CNTT'),('KN','Kiến trúc nội thất','CT'),('LA','Luật','LCT'),('LG','Logistics và quản lý chuỗi cung ứng','KTVT'),('QM','Quản trị Marketing','QT');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reward`
--

LOCK TABLES `reward` WRITE;
/*!40000 ALTER TABLE `reward` DISABLE KEYS */;
INSERT INTO `reward` VALUES (1,'K75KN0002','2026-01-03','Sinh viên 5 Tốt','11065');
/*!40000 ALTER TABLE `reward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `student_role` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  KEY `FK_ROLE_ST_idx` (`student_id`),
  CONSTRAINT `FK_ROLE_ST` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'K74QM0002','Lớp trưởng'),(2,'K74QM0003','Lớp phó'),(3,'K74QM0004','Bí thư');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scholarship`
--

DROP TABLE IF EXISTS `scholarship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scholarship` (
  `scholarship_id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `score_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `drl_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `scholarship_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `semester` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`scholarship_id`),
  KEY `FK_SS_ST_idx` (`student_id`),
  CONSTRAINT `FK_SS_ST` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scholarship`
--

LOCK TABLES `scholarship` WRITE;
/*!40000 ALTER TABLE `scholarship` DISABLE KEYS */;
INSERT INTO `scholarship` VALUES (1,'K75KN0004','Xuất sắc','Tốt','Giỏi','Kì 4');
/*!40000 ALTER TABLE `scholarship` ENABLE KEYS */;
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
  `citizen_id` varchar(12) NOT NULL,
  `class_id` varchar(10) NOT NULL,
  `status` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_no_UNIQUE` (`citizen_id`) /*!80000 INVISIBLE */,
  KEY `FK_ST_CL_idx` (`class_id`),
  CONSTRAINT `FK_ST_CL` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('K74QM0001','Vũ Thành','Đạt','2005-05-30','Nam','Thái Bình','0379871545','dat.vuthanh@gmail.com','03420002001','74QM01','Đang theo học'),('K74QM0002','Nghiêm Vũ Hoàng','Long','2005-01-19','Nam','Hà Nội','0398754842','mck@gmail.com','001205098915','74QM01','Đang theo học'),('K74QM0003','Phạm Nam','Hải','2005-01-20','Nam','Hà Nội','0359855648','wrxdie@gmail.com','001205005698','74QM01','Đang theo học'),('K74QM0004','Nguyễn Thảo','Linh','2005-04-28','Nữ','Hà Nội','0541984654','tlink@gmail.com','001305098252','74QM01','Đang theo học'),('K75HT0001','Nguyễn Minh','Tuấn','2006-01-06','Nam','Nam Định','0378459123','tuan.nguyenminh02@gmail.com','036202003181','75HT01','Đang theo học'),('K75KN0002','Trần Quốc','Huy','2001-11-02','Nam','Hà Nội','0984123675','huy.tranquoc01@gmail.com','001201011024','75KN01','Đang theo học'),('K75KN0003','Đỗ Nhật','Nam','2006-01-14','Nam','Long An','0156785412','nam.donhat03@gmail.com','039012584813','75KN01','Đang theo học'),('K75KN0004','Nguyễn Quang','Nam','2026-11-06','Nữ','Quảng Ngãi','0344874578','namnq@gmail.com','02332919441','75KN01','Đang theo học'),('K75LA0005','Nguyễn Đình','Khoa','2006-08-17','Nam','Hà Tĩnh','0659848797','khoa.nguyendinh@gmail.com','035894984498','75LA01','Đang theo học'),('K76EN0001','Lê Hoàng','Long','2007-01-06','Nam','Ninh Bình','0247214722','long.lehoang@gmail.com','038200007091','76EN01','Đang theo học'),('K76EN0002','Phan Thanh','Tùng','2007-02-27','Nam','Bình Dương','0978451236','tung.phanthanh@gmail.com','049200002271','76EN01','Đang theo học');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspension`
--

DROP TABLE IF EXISTS `suspension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suspension` (
  `suspension_id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NOT NULL,
  `reason` text NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`suspension_id`),
  KEY `FK_RSV_ST_idx` (`student_id`),
  CONSTRAINT `FK_RSV_ST` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspension`
--

LOCK TABLES `suspension` WRITE;
/*!40000 ALTER TABLE `suspension` DISABLE KEYS */;
INSERT INTO `suspension` VALUES (2,'K75LA0005','2026-01-08','2028-01-29','NVQS','Đã duyệt');
/*!40000 ALTER TABLE `suspension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` varchar(10) NOT NULL,
  `teacher_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `facu_id` varchar(45) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `FK_TC_FACU_idx` (`facu_id`),
  CONSTRAINT `FK_TC_FACU` FOREIGN KEY (`facu_id`) REFERENCES `faculties` (`facu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('CNTT0012','Nguyễn Thái Hoàng','CNTT'),('CNTT2201','Nguyễn Quang Cường','CNTT'),('CT0016','Trương Anh Phúc','CT'),('KHUD0021','Cao Việt Hưng','KHUD'),('KTVT0036','Lê Hải','KTVT'),('KTVT0069','Nguyễn Hoàng Công Sơn','KTVT'),('LCT0035','Cung Hoàng Giang','LCT'),('LCT0781','Ngô Hoàng Dương','LCT'),('QT0089','Trần Nhật Quân','QT');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-06  3:46:16
