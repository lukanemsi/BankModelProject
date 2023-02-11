CREATE TABLE IF NOT EXISTS cardholder (
   id serial PRIMARY KEY,
   first_name varchar(50) NOT NULL,
   last_name varchar(50) NOT NULL,
   email varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS card_info (
   card_number bigint NOT NULL,
   ccv int NOT NULL,
   expDate date NOT NULL,
   balance decimal NOT NULL,
   cardholder_id int NOT NULL,
   PRIMARY KEY (card_number),
   FOREIGN KEY (cardholder_ID) REFERENCES cardholder(id)
);

CREATE TABLE IF NOT EXISTS transaction_history(
   transaction_id serial primary key,
   cardholder_id int NOT NULL,
   card_number bigint NOT NULL,
   amount int NOT NULL,
   transaction_confirmed date DEFAULT NOW(),
   FOREIGN KEY(cardholder_id) REFERENCES cardholder(id)
);

CREATE OR REPLACE FUNCTION change_balance(c_number bigint,amount int)
RETURNS VOID AS $$
BEGIN
   UPDATE card_info SET balance = balance + amount WHERE card_info.card_number = c_number;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION transaction(from_card_number bigint,to_card_number bigint,amount int)
RETURNS VOID AS $$
BEGIN
   PERFORM change_balance(from_card_number,(-amount));
   PERFORM change_balance(to_card_number,amount);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_balances(IN holder_id INTEGER)
RETURNS TABLE(card_number bigint, balance numeric)
AS $$
BEGIN
  RETURN QUERY
  SELECT ci.card_number as card_number, ci.balance as balance
  FROM card_info ci JOIN cardholder ch ON ci.cardholder_id = ch.id AND ch.id = holder_id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION insert_transaction_history() RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO transaction_history (cardholder_id, card_number, amount)
  VALUES (NEW.cardholder_id, NEW.card_number ,NEW.balance - OLD.balance);
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER insert_transaction_history_trigger
AFTER UPDATE ON card_info
FOR EACH ROW
EXECUTE FUNCTION insert_transaction_history();


-- INSERTS

INSERT INTO cardholder (first_name, last_name, email)
VALUES
('John', 'Doe', 'johndoe@gmail.com'),
('Jane', 'Doe', 'janedoe@gmail.com'),
('Bob', 'Smith', 'bobsmith@gmail.com'),
('Amy', 'Johnson', 'amyjohnson@gmail.com'),
('Mike', 'Brown', 'mikebrown@gmail.com'),
('Sara', 'Davis', 'saradavis@gmail.com'),
('David', 'Wilson', 'davidwilson@gmail.com'),
('Emily', 'Johnson', 'emilyjohnson@gmail.com'),
('William', 'Taylor', 'williamtaylor@gmail.com'),
('Ashley', 'Lee', 'ashleylee@gmail.com');

INSERT INTO card_info (card_number, ccv, expDate, balance, cardholder_ID)
VALUES
(1234123412341, 123, '2025-12-01', 1000, 1),
(2345234523452, 234, '2026-01-01', 2000, 2),
(3456345634456, 345, '2026-02-01', 3000, 3),
(4567456745674, 456, '2026-03-01', 4000, 4),
(5678567856785, 567, '2026-04-01', 5000, 5);