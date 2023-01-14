CREATE TABLE messages (
    sender VARCHAR(50) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    date DATE NOT NULL,
    ad_aid INTEGER NOT NULL,
    FOREIGN KEY (ad_aid) REFERENCES advertisements(aid)
);