CREATE OR REPLACE FUNCTION find_customer_all(id BIGINT, name VARCHAR)
RETURNS TABLE(id BIGINT, code VARCHAR, name VARCHAR) AS $$
BEGIN
    RETURN QUERY SELECT c.id, c.code, c.name, c.phone, c.iban, c.surname, c.address FROM customer c;
END;
$$ LANGUAGE plpgsql;