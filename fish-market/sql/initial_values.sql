use fishmarkets;
INSERT INTO fishmarkets (year_of_opening, name) VALUES ('2015', 'Dunav doo');
INSERT INTO fishmarkets (year_of_opening, name) VALUES ('2012', 'Tisa str');
INSERT INTO fishmarkets (year_of_opening, name) VALUES ('2015', 'Sveza riba');

INSERT INTO fishes (sort, place_of_catch, price, available_quantity, fish_market_id) VALUES ('Smudj', 'Ribnjak Bager', '1100', '20', '3');
INSERT INTO fishes (sort, place_of_catch, price, available_quantity, fish_market_id) VALUES ('Saran', 'Dunav', '860', '30', '1');
INSERT INTO fishes (sort, place_of_catch, price, available_quantity, fish_market_id) VALUES ('Som', 'Tisa', '1300', '10', '2');
INSERT INTO fishes (sort, place_of_catch, price, available_quantity, fish_market_id) VALUES ('Saran', 'Ribnjak Ecka', '780', '12', '3');
INSERT INTO fishes (sort, place_of_catch, price, available_quantity, fish_market_id) VALUES ('Smudj', 'Dunav', '950', '15', '1');

INSERT INTO users (id, email, password, username) VALUES ('1', 'bojan@gmail.com', '$2a$10$YMlHvXoBTm8U2waK00TJeeGeHZZqwSJHSjflX7/HHygu3DBqG7Y0e', 'bojan');
INSERT INTO users (id, email, password, username) VALUES ('2', 'admin@gmail.com', '$2a$10$GEJ212hcD5gC6yzKIVDOCuDZzoe34CjdvAWaaFI7YIGCAno/avFHy', 'admin');

INSERT INTO roles (id, name) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES ('2', 'ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES ('1', '2');
INSERT INTO users_roles (user_id, role_id) VALUES ('2', '1');
