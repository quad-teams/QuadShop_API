-- V1__init_schema.sql

CREATE TABLE images (
                        id      VARCHAR(255) NOT NULL,
                        url     VARCHAR(255) NOT NULL,
                        product_id  BIGINT          NOT NULL,
                        CONSTRAINT pk_images PRIMARY KEY (id),
                            CONSTRAINT fk_sizes_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE products (
                          id          BIGINT       NOT NULL,
                          name        VARCHAR(255)    NOT NULL,
                          description TEXT            NOT NULL,
                          price       NUMERIC(19, 2)  NOT NULL,
                          category    VARCHAR(255)    NOT NULL,
                          CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE sizes (
                       id          BIGINT       NOT NULL,
                       name        VARCHAR(255)    NOT NULL,
                       product_id  BIGINT          NOT NULL,
                       CONSTRAINT pk_sizes PRIMARY KEY (id),
                       CONSTRAINT fk_sizes_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE product_images (
                                product_id  BIGINT          NOT NULL,
                                image_id    VARCHAR(255)    NOT NULL,
                                CONSTRAINT fk_product_images_product FOREIGN KEY (product_id) REFERENCES products (id),
                                CONSTRAINT fk_product_images_image   FOREIGN KEY (image_id)   REFERENCES images (id)
);

CREATE TABLE orders (
                        id          BIGINT       NOT NULL,
                        status      VARCHAR(50)     NOT NULL,
                        created_at  TIMESTAMP       NOT NULL,
                        CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE order_items (
                             id                  BIGINT       NOT NULL,
                             quantity            INT             NOT NULL,
                             price_at_purchase   NUMERIC(19, 2)  NOT NULL,
                             order_id            BIGINT          NOT NULL,
                             product_id          BIGINT          NOT NULL,
                             CONSTRAINT pk_order_items PRIMARY KEY (id),
                             CONSTRAINT fk_order_items_order   FOREIGN KEY (order_id)   REFERENCES orders (id),
                             CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products (id)
);