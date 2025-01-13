
CREATE TABLE IF NOT EXISTS shop (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(20) NOT NULL,
                      address VARCHAR(255) NOT NULL,
                      description TEXT NOT NULL,
                      latitude VARCHAR(20) NOT NULL,
                      longitude VARCHAR(20) NOT NULL,
                      category VARCHAR(20) NOT NULL,
                      image VARCHAR(255) NOT NULL,
                      is_open BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS owner (
                       id INT PRIMARY KEY AUTO_INCREMENT ,
                       shop_id INT
);


