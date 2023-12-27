--BIGSERIAL creates a BIGINT sequence
--if now we would have to CREATE SQUENCE sequence_name;
--then swap BIGSERIAL for BIGINT DEFAULT nextval(sequence_name)
CREATE TABLE customer(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    age INT NOT NULL);