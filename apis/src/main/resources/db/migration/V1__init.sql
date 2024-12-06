-- 테이블 생성
CREATE TABLE Brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS coordinations (
   id INT AUTO_INCREMENT PRIMARY KEY,
   category VARCHAR(255) NOT NULL,
   price INT NOT NULL,
   brand_id INT NOT NULL,
   CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands (id) ON DELETE RESTRICT
);

-- Brand 테이블에 데이터 삽입
INSERT INTO Brands (name)
SELECT DISTINCT
    name
FROM CSVREAD('classpath:/db/migration/brand_data.csv', NULL, 'UTF-8');

-- Coordination 테이블에 데이터 삽입
INSERT INTO Coordinations (brand_id, category, price)
SELECT
    b.id AS brand_id,
    c.category,
    c.price
FROM CSVREAD('classpath:/db/migration/coordination_data.csv', NULL, 'UTF-8') AS c
         JOIN Brands b
              ON b.name = c.brand_name;

