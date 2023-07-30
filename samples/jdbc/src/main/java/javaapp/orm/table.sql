-- Table for jsc project in Mariadb. --

-- User table --
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL UNIQUE,  
  `detail` varchar(255) DEFAULT NULL,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ext` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Trace table --
CREATE TABLE `Trace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) NOT NULL UNIQUE,  
  `user_id` int(11) NOT NULL,
  `page_url` varchar(255) NOT NULL,
  `metric` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ext` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- TraceAt table --
CREATE TABLE `TraceAt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trace_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ext` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

