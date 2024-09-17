
-- Insert into Category
INSERT INTO category (id, name, description)
VALUES
    (1, 'Electronics', 'Devices and gadgets'),
    (2, 'Books', 'Various kinds of books'),
    (3, 'Clothing', 'Apparel and accessories');

-- Insert into Product
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES
    (1, 'Smartphone', 'Latest model smartphone with 5G', 50, 699.99, 1),
    (2, 'Laptop', 'High-performance laptop for professionals', 30, 999.99, 1),
    (3, 'Novel', 'Best-selling fiction novel', 100, 14.99, 2),
    (4, 'T-shirt', 'Cotton T-shirt available in various sizes', 200, 19.99, 3);
