-- Create customers table
CREATE TABLE CUSTOMERS_TABLE (
    CUSTOMER_ID INT AUTO_INCREMENT PRIMARY KEY,
    CUSTOMER_NAME VARCHAR(255),
    CONTACT_NAME VARCHAR(255),
    ADDRESS VARCHAR(255),
    CITY VARCHAR(255),
    POSTAL_CODE VARCHAR(10),
    COUNTRY VARCHAR(255)
);

CREATE TABLE EMPLOYEES_TABLE (
    EMPLOYEE_ID INT AUTO_INCREMENT PRIMARY KEY,
    LAST_NAME VARCHAR(255),
    FIRST_NAME VARCHAR(255),
    BIRTH_DATE DATE,
    PHOTO VARCHAR(255),
    NOTES TEXT
);

CREATE TABLE SHIPPERS_TABLE (
    SHIPPER_ID INT AUTO_INCREMENT PRIMARY KEY,
    SHIPPER_NAME VARCHAR(255),
    PHONE VARCHAR(255)
);

CREATE TABLE CATEGORIES_TABLE (
    CATEGORY_ID INT AUTO_INCREMENT PRIMARY KEY,
    CATEGORY_NAME VARCHAR(255),
    DESCRIPTION TEXT
);

CREATE TABLE SUPPLIERS_TABLE (
    SUPPLIER_ID INT AUTO_INCREMENT PRIMARY KEY,
    SUPPLIER_NAME VARCHAR(255),
    CONTACT_NAME VARCHAR(255),
    ADDRESS VARCHAR(255),
    CITY VARCHAR(255),
    POSTAL_CODE VARCHAR(20),
    COUNTRY VARCHAR(255),
    PHONE VARCHAR(20)
);

-- Create Orders table 
CREATE TABLE ORDERS_TABLE (
    ORDER_ID int PRIMARY KEY,
    CUSTOMER_ID int,
    EMPLOYEE_ID int,
    ORDER_DATE date,
    SHIPPER_ID int,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS_TABLE(CUSTOMER_ID),
    FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES_TABLE(EMPLOYEE_ID),
    FOREIGN KEY (SHIPPER_ID) REFERENCES SHIPPERS_TABLE(SHIPPER_ID)
);

-- Create the products table
CREATE TABLE PRODUCTS_TABLE (
    PRODUCT_ID INT PRIMARY KEY,
    PRODUCT_NAME VARCHAR(255),
    SUPPLIER_ID INT,
    CATEGORY_ID INT,
    UNIT VARCHAR(255),
    PRICE DECIMAL(10,2),
    FOREIGN KEY (SUPPLIER_ID) REFERENCES SUPPLIERS_TABLE(SUPPLIER_ID),
    FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES_TABLE(CATEGORY_ID)
);

-- Create the ordersDetails table
CREATE TABLE ORDERS_DETAILS_TABLE (
    ORDER_DETAIL_ID INT AUTO_INCREMENT PRIMARY KEY,
    ORDER_ID INT,
    PRODUCT_ID INT,
    QUANTITY INT,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS_TABLE(ORDER_ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS_TABLE(PRODUCT_ID)
);

-- Create the navbar table
--CREATE TABLE NAVBAR_ELEMENTS_TABLE (
--    NAV_ELEMENT_ID INT AUTO_INCREMENT PRIMARY KEY,
--    NAV_TITLE VARCHAR(255),
--    NAV_ELEMENT_TABLE VARCHAR(255),
--    FILTER VARCHAR(255),
--    SYNTAX VARCHAR(255),
--    DEFINITION VARCHAR(255)
--);


