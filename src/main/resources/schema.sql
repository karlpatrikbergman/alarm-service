
-- For development
-- DROP TABLE IF EXISTS node;
-- CREATE TABLE node (
-- For production
CREATE TABLE IF NOT EXISTS node (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  ip_address varchar(255) DEFAULT NULL,
  port bigint(20) NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);