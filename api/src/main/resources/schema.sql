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
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS rider
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(20) NOT NULL,
    status    VARCHAR(40) NOT NULL,
    latitude  DOUBLE,
    longitude DOUBLE
);

CREATE TABLE IF NOT EXISTS login_user
(
    token   VARCHAR(36) PRIMARY KEY,
    role    VARCHAR(10) NOT NULL,
    user_id INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id             VARCHAR(30) PRIMARY KEY,
    shop_id        INT          NOT NULL,
    customer_id    INT          NOT NULL,
    total_price    INT          NOT NULL,
    delivery_fee   INT          NOT NULL,
    status         VARCHAR(20)  NOT NULL,
    address        VARCHAR(255) NOT NULL,
    is_contactless Boolean      NOT NULL,

    FOREIGN KEY (shop_id) REFERENCES shop (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS order_menu
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(30) NOT NULL,
    name     VARCHAR(20) NOT NULL,
    price    INT         NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS delivery
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    order_id     VARCHAR(30) NOT NULL,
    location_lat DOUBLE      NOT NULL,
    location_lon DOUBLE      NOT NULL,
    rider_id     INT,
    match_status BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

