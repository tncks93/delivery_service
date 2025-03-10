CREATE TABLE IF NOT EXISTS shop
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(20)  NOT NULL,
    address     VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    latitude    VARCHAR(20),
    longitude   VARCHAR(20),
    category    VARCHAR(20)  NOT NULL,
    image       VARCHAR(255),
    is_open     BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS menu
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    image       VARCHAR(255),
    description TEXT        NOT NULL,
    price       INT         NOT NULL,
    is_on_sale  BOOLEAN DEFAULT TRUE,
    shop_id     INT         NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shop (id)
);

CREATE TABLE IF NOT EXISTS owner
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    shop_id INT
);

CREATE TABLE IF NOT EXISTS customer
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(20)  NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS login_user
(
    token   VARCHAR(36) PRIMARY KEY,
    role    VARCHAR(10) NOT NULL,
    user_id INT         NOT NULL
);


