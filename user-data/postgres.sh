#!/bin/bash
DATABASE_PASS='admin123'
sudo yum update -y
sudo yum install epel-release -y
sudo yum install git zip unzip -y
sudo yum install postgresql-server postgresql-contrib -y

# Initialize PostgreSQL database and enable automatic start
sudo postgresql-setup --initdb
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Set up PostgreSQL user and database
sudo -u postgres psql -c "ALTER USER postgres PASSWORD '$DATABASE_PASS';"
sudo -u postgres psql -c "CREATE DATABASE accounts;"
sudo -u postgres psql -c "CREATE USER admin WITH ENCRYPTED PASSWORD 'admin123';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE accounts TO admin;"

# Clone the project and restore the dump file for the application
cd /tmp/
git clone -b main https://github.com/hkhcoder/vprofile-project.git
sudo -u postgres psql accounts < /tmp/vprofile-project/src/main/resources/db_backup.sql

# Configure PostgreSQL to accept remote connections
sudo bash -c "echo \"listen_addresses = '*'\" >> /var/lib/pgsql/data/postgresql.conf"
sudo bash -c "echo \"host all all 0.0.0.0/0 md5\" >> /var/lib/pgsql/data/pg_hba.conf"
sudo systemctl restart postgresql

# Start the firewall and allow PostgreSQL to be accessed from port 5432
sudo yum install firewalld -y
sudo systemctl start firewalld
sudo systemctl enable firewalld
sudo firewall-cmd --get-active-zones
sudo firewall-cmd --zone=public --add-port=5432/tcp --permanent
sudo firewall-cmd --reload
sudo systemctl restart firewalld
