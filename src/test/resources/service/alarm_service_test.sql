TRUNCATE TABLE node
TRUNCATE TABLE alarm

INSERT INTO node (ip_address, port, user_name, password) VALUES ('11.22.33.44', 80, 'david', 'bowie')
INSERT INTO node (ip_address, port, user_name, password) VALUES ('55.66.77.88', 80, 'sheila', 'e')

INSERT INTO alarm (alarm_index, alarm_object, alarm_fault_status, alarm_mgmt_name, alarm_inv_phys_index_or_zero, alarm_inv_logical_index_or_zero, alarm_type, alarm_cause, alarm_text, alarm_severity, alarm_created_time, alarm_last_change_time, alarm_seq_number, alarm_ne_name, alarm_ne_ip_address) VALUES ('0', 'alarm_object', 'alarm_fault_status', 'alarm_mgmt_name', 'alarm_inv_phys_index_or_zero', 'alarm_inv_logical_index_or_zero', 'alarm_type', 'alarm_cause', 'alarm_text', 'alarm_severity', 'alarm_created_time', 'alarm_last_change_time', 'alarm_seq_number', 'thin white duke', '44.33.22.11')