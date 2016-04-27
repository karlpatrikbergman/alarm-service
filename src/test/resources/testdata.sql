TRUNCATE TABLE node
TRUNCATE TABLE alarm


INSERT INTO node (ip_address, port, user_name, password) VALUES ('99.99.99.99', 80, 'root', '')
INSERT INTO node (ip_address, port, user_name, password) VALUES ('88.88.88.88', 80, 'root', '')

INSERT INTO alarm (alarm_index, alarm_object, alarm_fault_status, alarm_mgmt_name, alarm_inv_phys_index_or_zero, alarm_inv_logical_index_or_zero, alarm_type, alarm_cause, alarm_text, alarm_severity, alarm_created_time, alarm_last_change_time, alarm_seq_number, alarm_ne_name, alarm_ne_ip_address) VALUES ('0', 'alarm_object', 'alarm_fault_status', 'alarm_mgmt_name', 'alarm_inv_phys_index_or_zero', 'alarm_inv_logical_index_or_zero', 'alarm_type', 'alarm_cause', 'alarm_text', 'alarm_severity', 'alarm_created_time', 'alarm_last_change_time', 'alarm_seq_number', 'thin white duke', '99.99.99.99')
