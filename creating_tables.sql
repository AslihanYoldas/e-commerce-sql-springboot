-- DELETE ALL TABLES
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

CREATE TABLE Admin(
admin_id SERIAL PRIMARY KEY,
admin_role VARCHAR(100) NOT NULL,
admin_name VARCHAR(100) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Shopping_Website(
website_name VARCHAR(100) PRIMARY KEY,
contact_no VARCHAR(30),
admin_id INT UNIQUE NOT NULL,
FOREIGN KEY (admin_id) REFERENCES Admin(admin_id)
);


CREATE TABLE Category(
category_id SERIAL PRIMARY KEY,
category_name VARCHAR(100) UNIQUE NOT NULL,
parent_category_id INT
);

CREATE TABLE Supplier(
supplier_id SERIAL PRIMARY KEY,
supplier_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Product(
product_id SERIAL PRIMARY KEY,
product_name VARCHAR(200) UNIQUE NOT NULL,
product_price DECIMAL(8, 2) NOT NULL CHECK (product_price > 0),
product_desc VARCHAR(500),
product_image TEXT,
supplier_id INT NOT NULL,
FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

CREATE TABLE Product_Variation(
variation_id SERIAL PRIMARY KEY,
product_id INT NOT NULL,
variation_price DECIMAL(8,2) CHECK (variation_price > 0),
variation_size VARCHAR(20) ,
variation_color VARCHAR(30),
variation_SKU VARCHAR(20) UNIQUE NOT NULL,
variation_stock_quantity INT CHECK (variation_stock_quantity >= 0),
UNIQUE (product_id, variation_color, variation_size),
FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE Customer(
customer_id SERIAL PRIMARY KEY,
customer_name VARCHAR(50) NOT NULL,
customer_registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
customer_phone_number VARCHAR(30),
customer_email VARCHAR(50) UNIQUE NOT NULL,
customer_password VARCHAR(100) NOT NULL
);

CREATE TABLE Address(
address_id SERIAL PRIMARY KEY,
customer_id INT NOT NULL,
city VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
postal_code VARCHAR(20),
address_line VARCHAR(150) NOT NULL,
is_defaullt BOOLEAN DEFAULT FALSE,
FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE "Order"(
order_id SERIAL PRIMARY KEY,
customer_id INT NOT NULL,
order_status VARCHAR(20) NOT NULL,
order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
order_total DECIMAL(8,2) NOT NULL CHECK (order_total > 0) ,
FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Payment(
payment_id SERIAL PRIMARY KEY,
customer_id INT NOT NULL,
order_id INT NOT NULL UNIQUE,
payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
payment_status VARCHAR(20) NOT NULL,
payment_amount DECIMAL(8,2) NOT NULL  CHECK (payment_amount > 0),
FOREIGN KEY (order_id) REFERENCES "Order"(order_id),
FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Cart(
cart_id SERIAL PRIMARY KEY,
customer_id INT NOT NULL ,
FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Cart_Item(
cart_item_id SERIAL PRIMARY KEY,
product_id INT NOT NULL,
cart_id INT NOT NULL,
cart_item_quantity INT,
cart_item_unit_price INT,
FOREIGN KEY (cart_id) REFERENCES Cart(cart_id),
FOREIGN KEY (product_id) REFERENCES Product(product_id)
);


CREATE TABLE Ordered_Item(
ordered_item_id SERIAL PRIMARY KEY,
order_id INT NOT NULL,
product_id INT NOT NULL,
ordered_item_status VARCHAR(20) NOT NULL,
ordered_item_quantity INT,
ordered_item_unit_price DECIMAL(8,2) NOT NULL CHECK (ordered_item_unit_price > 0) ,
FOREIGN KEY (order_id) REFERENCES "Order"(order_id),
FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE Review(
review_id SERIAL PRIMARY KEY,
ordered_item_id INT NOT NULL UNIQUE,
review_rating_value INT CHECK (review_rating_value BETWEEN 1 AND 5),
review_comment VARCHAR(200),
review_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (ordered_item_id) REFERENCES Ordered_Item(ordered_item_id)
);

CREATE TABLE Tracking_Detail(
tracking_id SERIAL PRIMARY KEY,
ordered_item_id INT UNIQUE NOT NULL,
tracking_status VARCHAR(30) NOT NULL,
tracking_location VARCHAR(50),
tracking_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (ordered_item_id) REFERENCES Ordered_Item(ordered_item_id)
);

CREATE TABLE Shipping_Method(
shippinng_id SERIAL PRIMARY KEY,
shipping_name VARCHAR(100) NOT NULL,
estimated_delivery_days INT CHECK (estimated_delivery_days > 0),
shipping_cost DECIMAL(6,2) CHECK (shipping_cost >= 0),
is_active BOOLEAN DEFAULT TRUE
);

ALTER TABLE "Order"
ADD COLUMN shipping_id INT NOT NULL,
ADD FOREIGN KEY (shipping_id) REFERENCES Shipping_Method(shippinng_id);



