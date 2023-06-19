# Select image repo
FROM mysql:latest

# Set environment variable
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=demo

# Add querries
ADD ./onlinevotingsystem_account.sql /docker-entrypoint-initdb.d/