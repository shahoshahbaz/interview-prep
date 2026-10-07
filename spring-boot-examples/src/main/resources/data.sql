INSERT INTO books (title, author, genre, pages, price, status) VALUES
                                                                   ('Clean Code', 'Robert Martin', 'Tech', 431, 35.99, 'PUBLISHED'),
                                                                   ('The Pragmatic Programmer', 'David Thomas', 'Tech', 352, 42.00, 'PUBLISHED'),
                                                                   ('Dune', 'Frank Herbert', 'Sci-Fi', 688, 18.50, 'PUBLISHED'),
                                                                   ('1984', 'George Orwell', 'Fiction', 328, 12.99, 'PUBLISHED'),
                                                                   ('Effective Java', 'Joshua Bloch', 'Tech', 412, 45.00, 'DRAFT');


-- ===========================
-- Ordering module seed data
-- ===========================
INSERT INTO ordering_books (title, author, price, stock) VALUES
                                                             ('Clean Code', 'Robert Martin', 35.99, 10),
                                                             ('The Pragmatic Programmer', 'David Thomas', 42.00, 5),
                                                             ('Dune', 'Frank Herbert', 18.50, 3),
                                                             ('1984', 'George Orwell', 12.99, 8),
                                                             ('Effective Java', 'Joshua Bloch', 45.00, 0);


-- ===========================
-- JPA module seed data
-- ===========================
INSERT INTO jpa_authors (name, email) VALUES
                                          ('Robert Martin', 'robert@example.com'),
                                          ('Joshua Bloch', 'joshua@example.com'),
                                          ('Frank Herbert', 'frank@example.com');

INSERT INTO jpa_books (title, genre, price, author_id) VALUES
                                                           ('Clean Code', 'Tech', 35.99, 1),
                                                           ('The Clean Coder', 'Tech', 30.00, 1),
                                                           ('Effective Java', 'Tech', 45.00, 2),
                                                           ('Java Concurrency', 'Tech', 40.00, 2),
                                                           ('Dune', 'Sci-Fi', 18.50, 3),
                                                           ('Dune Messiah', 'Sci-Fi', 15.00, 3);

-- ===========================
-- Pagination module seed data
-- ===========================
INSERT INTO products (name, category, brand, price, stock) VALUES
                                                               ('iPhone 15', 'Electronics', 'Apple', 999.99, 50),
                                                               ('MacBook Pro', 'Electronics', 'Apple', 2499.99, 20),
                                                               ('AirPods Pro', 'Electronics', 'Apple', 249.99, 100),
                                                               ('Galaxy S24', 'Electronics', 'Samsung', 899.99, 45),
                                                               ('Galaxy Tab', 'Electronics', 'Samsung', 649.99, 30),
                                                               ('Surface Pro', 'Electronics', 'Microsoft', 1299.99, 25),
                                                               ('Xbox Controller', 'Gaming', 'Microsoft', 59.99, 200),
                                                               ('PS5 Controller', 'Gaming', 'Sony', 69.99, 150),
                                                               ('Nintendo Switch', 'Gaming', 'Nintendo', 299.99, 80),
                                                               ('Gaming Chair', 'Gaming', 'SecretLab', 499.99, 15),
                                                               ('Running Shoes', 'Sports', 'Nike', 129.99, 75),
                                                               ('Yoga Mat', 'Sports', 'Lululemon', 49.99, 120),
                                                               ('Protein Powder', 'Sports', 'Optimum', 59.99, 0),
                                                               ('Tennis Racket', 'Sports', 'Wilson', 89.99, 0),
                                                               ('Coffee Maker', 'Kitchen', 'Breville', 199.99, 40),
                                                               ('Air Fryer', 'Kitchen', 'Ninja', 119.99, 60),
                                                               ('Blender', 'Kitchen', 'Vitamix', 449.99, 25),
                                                               ('Toaster', 'Kitchen', 'Cuisinart', 39.99, 0);