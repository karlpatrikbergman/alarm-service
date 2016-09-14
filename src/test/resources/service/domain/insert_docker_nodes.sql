TRUNCATE TABLE node
TRUNCATE TABLE alarm

INSERT INTO node (ip_address, port, user_name, password) VALUES ('172.18.0.2', 80, 'root', '')
INSERT INTO node (ip_address, port, user_name, password) VALUES ('172.18.0.3', 80, 'root', '')

