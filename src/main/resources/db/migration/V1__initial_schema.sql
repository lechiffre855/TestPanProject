CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          parent_id BIGINT,
                          CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE SET NULL
);

ALTER TABLE category ADD CONSTRAINT unique_name UNIQUE (name);

CREATE INDEX idx_category_parent_id ON category(parent_id);