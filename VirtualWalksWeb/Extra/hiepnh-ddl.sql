delimiter $$

CREATE TABLE `newuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1$$



delimiter $$

CREATE TABLE `walk` (
  `idwalk` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `isactive` varchar(45) DEFAULT NULL,
  `city_idcity` varchar(45) DEFAULT NULL,
  `county_idcounty` varchar(45) DEFAULT NULL,
  `createuser_idUser` int(11) DEFAULT NULL,
  `updateuser_idUser1` int(11) DEFAULT NULL,
  PRIMARY KEY (`idwalk`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1$$

