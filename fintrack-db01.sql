-- Alter default privileges for future tables to grant all to fintrack-administrator
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON TABLES TO "admin";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON SEQUENCES TO "admin";


-- Connect to the newly created database
\c fintrackdb01

-- Create users table
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE roles (
                       role_id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL,
                       description TEXT
);

-- Create user_roles table
CREATE TABLE user_roles (
                            user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                            role_id INT REFERENCES roles(role_id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- Create sessions table
CREATE TABLE sessions (
                          session_id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                          session_token VARCHAR(255) UNIQUE NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          expires_at TIMESTAMP
);
