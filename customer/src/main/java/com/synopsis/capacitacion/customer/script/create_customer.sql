CREATE OR REPLACE FUNCTION create_customer_with_products(
    in_code VARCHAR,
    in_name VARCHAR,
    in_phone VARCHAR,
    in_iban VARCHAR,
    in_surname VARCHAR,
    in_address VARCHAR,
    in_products JSON
) RETURNS VOID AS $$
DECLARE
    product RECORD;
BEGIN
    INSERT INTO customer (code, name, phone, iban, surname, address)
    VALUES (in_code, in_name, in_phone, in_iban, in_surname, in_address)
    RETURNING id INTO product.customer_id;

    FOR product IN SELECT * FROM json_array_elements(in_products) LOOP
        INSERT INTO customer_product (product_name, customer_id)
        VALUES (product->>'productName', customer_id);
    END LOOP;
END;
$$ LANGUAGE plpgsql;
