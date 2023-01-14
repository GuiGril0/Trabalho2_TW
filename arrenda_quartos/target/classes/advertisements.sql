DROP TABLE IF EXISTS advertisements;

CREATE TABLE advertisements (
    advertiser VARCHAR(50) NOT NULL,
    typead VARCHAR(15) NOT NULL,
    statead VARCHAR(15) NOT NULL,
    price NUMERIC NOT NULL,
    gender VARCHAR(15) NOT NULL,
    localad VARCHAR(100) NOT NULL,
    typology VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(1000) NOT NULL,
    aid SERIAL PRIMARY KEY
);