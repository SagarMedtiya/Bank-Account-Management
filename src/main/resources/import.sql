CREATE TABLE IF NOT EXISTS accounts(
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions(
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(50) UNIQUE NOT NULL,
    from_account VARCHAR(50) NOT NULL,
    to_account VARCHAR(50),
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description TEXT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'COMPLETED',
    balance_after DECIMAL(15,2)
);


INSERT INTO accounts (accountNumber, customerName, email, phone, accountType, balance) VALUES
                                                                                           ('ACC001', 'Sagar Medtiya', 'sagar.medtiya@gmail.com', '+1234567890', 'SAVINGS', 1000.00),
                                                                                           ('ACC002', 'Arjun Patel', 'arjun.patel@gmail.com', '+0987654321', 'CHECKING', 500.00),
                                                                                           ('ACC003', 'Tvisha Shah', 'tvisha.shah@gmail.com', '+1122334455', 'SAVINGS', 1500.00);