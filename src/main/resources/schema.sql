
-- For development
-- DROP TABLE IF EXISTS node;
-- CREATE TABLE node (
-- For production
CREATE TABLE IF NOT EXISTS node (
  ip_address varchar(255) DEFAULT NULL,
  port bigint(20) NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (ip_address)
);

CREATE TABLE IF NOT EXISTS alarm (
    alarm_index varchar(255) NOT NULL,
    alarm_object varchar(255) DEFAULT NULL,
    alarm_fault_status varchar(255) DEFAULT NULL,
    alarm_mgmt_name varchar(255) DEFAULT NULL,
    alarm_inv_phys_index_or_zero varchar(255) DEFAULT NULL,
    alarm_inv_logical_index_or_zero varchar(255) DEFAULT NULL,
    alarm_type varchar(255) DEFAULT NULL,
    alarm_cause varchar(255) DEFAULT NULL,
    alarm_text varchar(255) DEFAULT NULL,
    alarm_severity varchar(255) DEFAULT NULL,
    alarm_created_time varchar(255) DEFAULT NULL,
    alarm_last_change_time varchar(255) DEFAULT NULL,
    alarm_seq_number varchar(255) DEFAULT NULL,
    alarm_ne_name varchar(255) DEFAULT NULL,
    alarm_ne_ip_address varchar(255) NOT NULL,
    PRIMARY KEY (alarm_ne_ip_address, alarm_index)
);