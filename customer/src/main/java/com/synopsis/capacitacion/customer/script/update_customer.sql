CREATE OR REPLACE FUNCTION update_customer_name(id BIGINT, name VARCHAR)
RETURNS VOID AS $$
BEGIN
    UPDATE customer SET name = name WHERE id = id;
END;
$$ LANGUAGE plpgsql;