# Assignment

## Có thể xem demo và test trên swagger tại:
http://18.139.176.173:8080/swagger-ui.html#/

## Các phần mềm cần thiết
- Java 8 trở lên
- MYSQL
- Maven

## Cấu trúc database
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    status INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

## Dữ liệu mẫu
INSERT INTO products (name, description, price, stock_quantity) VALUES
('Laptop', 'A high-performance laptop', 1200.00, 50),
('Smartphone', 'A latest model smartphone', 800.00, 200),
('Headphones', 'Noise-cancelling over-ear headphones', 150.00, 100),
('Monitor', '27-inch 4K UHD monitor', 400.00, 75),
('Keyboard', 'Mechanical keyboard with RGB lighting', 100.00, 150);

INSERT INTO orders (order_date, customer_name, address, email, phone_number, status, total_amount) VALUES
('2024-06-01 10:00:00', 'John Doe', '123 Elm Street', 'johndoe@example.com', '123-456-7890', 0, 1600.00),
('2024-06-02 11:30:00', 'Jane Smith', '456 Oak Avenue', 'janesmith@example.com', '098-765-4321', 1, 950.00);

INSERT INTO order_details (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 1200.00),  -- Đơn hàng 1, Sản phẩm 1 (Laptop)
(1, 3, 2, 150.00),   -- Đơn hàng 1, Sản phẩm 3 (Headphones)
(2, 2, 1, 800.00),   -- Đơn hàng 2, Sản phẩm 2 (Smartphone)
(2, 5, 1, 100.00);   -- Đơn hàng 2, Sản phẩm 5 (Keyboard)

## Cấu hình
File cấu hình nằm trong: src/main/resources/application.properties
app.datasource.default.url=jdbc:mariadb://localhost:3306/assignment?useSSL=false
app.datasource.default.username=root  ( Cấu hình username )
app.datasource.default.password=123456 ( Cấu hình password )

## Chạy project
Run hàm main trong file App.java
