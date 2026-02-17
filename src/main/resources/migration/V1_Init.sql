CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT NOT NULL,
                          price DECIMAL(19, 2) NOT NULL,
                          category VARCHAR(255) NOT NULL
);

CREATE TABLE images (
                        id VARCHAR(255) PRIMARY KEY,
                        url VARCHAR(255) NOT NULL,
                        product_id BIGINT NOT NULL,
                        CONSTRAINT fk_images_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE sizes (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       product_id BIGINT NOT NULL,
                       CONSTRAINT fk_sizes_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE product_images (
                                product_id BIGINT NOT NULL,
                                image_id VARCHAR(255) NOT NULL,
                                CONSTRAINT fk_product_images_product FOREIGN KEY (product_id) REFERENCES products (id),
                                CONSTRAINT fk_product_images_image FOREIGN KEY (image_id) REFERENCES images (id)
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        status VARCHAR(50) NOT NULL
);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             quantity INT NOT NULL,
                             price_at_purchase DECIMAL(19, 2) NOT NULL,
                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id),
                             CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products (id)
);