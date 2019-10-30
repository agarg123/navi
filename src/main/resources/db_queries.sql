create database mayavi;

CREATE TABLE `api_responses` (
  `id` varchar(255) NOT NULL,
  `api_call_name` varchar(255) DEFAULT NULL,
  `api_call_url` varchar(255) DEFAULT NULL,
  `response` text,
  `params` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;