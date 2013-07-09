CREATE TABLE doctor (
	id INT NOT NULL,
	fullname VARCHAR(100),
	username VARCHAR(50),
	password VARCHAR(50),
	current_ip VARCHAR(45),
	last_login_id INT DEFAULT 0,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `ip_address` varchar(45) NOT NULL,
  `default_profile_url` varchar(200) NOT NULL,
  `camera_user` varchar(45) NOT NULL,
  `camera_pass` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `device` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `serial_number` varchar(45) NOT NULL,
  `url` varchar(200) NOT NULL,
  `onvif_version` varchar(50) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `doctor_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `captcha` varchar(150) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `audio_file` varchar(45) DEFAULT NULL,
  `feature_file` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `doctor__idx` (`doctor_id`),
  CONSTRAINT `fk_doctor_login` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `doctor_patient` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`doctor_id`,`patient_id`),
  KEY `fk_doctor_id_idx` (`doctor_id`),
  KEY `fk_patient_id_idx` (`patient_id`),
  CONSTRAINT `fk_doctor_patient_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_doctor_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `patient_device` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`patient_id`,`device_id`),
  KEY `fk_patient_device_id_idx` (`patient_id`),
  KEY `fk_device_patient_id_idx` (`device_id`),
  CONSTRAINT `fk_patient_device_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_device_patient_id` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `consultation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `expected_time` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT 'NEW',
  PRIMARY KEY (`id`),
  KEY `fk_doctor_consultation_idx` (`doctor_id`),
  KEY `fk_patient_consultation_idx` (`patient_id`),
  CONSTRAINT `fk_doctor_consultation` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_consultation` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

CREATE TABLE `screenshot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consultation_id` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `file_name` varchar(45) NOT NULL,
  `snapshotcol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_snapshot_session_idx` (`consultation_id`),
  CONSTRAINT `fk_screenshot_consultation` FOREIGN KEY (`consultation_id`) REFERENCES `consultation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

