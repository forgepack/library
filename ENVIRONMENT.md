# __Environment Configuration__

## 1. PREREQUISITES

| Tool              | Minimum version |                                                          Download | Details                            |
|:------------------|:---------------:|------------------------------------------------------------------:|:-----------------------------------|
| __Java JDK__      |       17        |              [OpenJDK 17](https://adoptium.net/temurin/releases/) | Project's main runtime             |
| __PostgreSQL__    |      17.5       |                [PostgreSQL](https://www.postgresql.org/download/) | Primary SGBD with PostGIS          |
| __Maven__         |      3.8+       |             [Apache Maven](https://maven.apache.org/download.cgi) | Build tool e dependency management |
| __IntelliJ IDEA__ |     2024.1+     |             [JetBrains](https://www.jetbrains.com/idea/download/) | IDE recommended                    |
| __Docker__        |      20.0+      | [Docker Desktop](https://www.docker.com/products/docker-desktop/) | Containerization                   |
| __DBeaver__       |      23.0+      |                        [DBeaver CE](https://dbeaver.io/download/) | Client PostgreSQL                  |

## 2. __PostgreSQL + PostGIS__ CONFIGURATION

```sql
-- create database
CREATE DATABASE maps;

-- connect to the database
\c maps;

-- enable PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- create schema
CREATE SCHEMA IF NOT EXISTS maps;

-- verify installation
SELECT version();
SELECT PostGIS_Version();
```

## 3. ENVIRONMENT VARIABLES

create the `.env` file in the project root
```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=maps
DB_USERNAME=postgres
DB_PASSWORD=password
DB_SCHEMA=maps

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here_min_256_bits
JWT_EXPIRATION=86400000

# server Configuration
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/maps

# profile configuration (dev, test, prd)
SPRING_PROFILES_ACTIVE=dev
```

## 4. HOW TO START

### 4.1. Installation & Setup

```bash
# clone the repository
git clone https://github.com/gadelhati/maps-back
cd maps-back
# add remote repository
git remote add upstream https://github.com/gadelhati/maps-back
# install dependencies
mvn clean install
# run tests
mvn test
# integrity check
mvn validate
# clean, test and generates the JaCoCo test coverage report
mvn clean test jacoco:report
# target: maintain coverage above 5%
# configure the database

# configure environment variables
cp .env.example .env
# edite o .env

# install dependencies
mvn clean install

# execute tests
mvn test

# execute the application
mvn spring-boot:run
```

### 4.2. Development Commands

```bash
# run in developer mode (CSRF disabled)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# run in production mode (CSRF enabled)  
mvn spring-boot:run -Dspring-boot.run.profiles=prd

# run tests with coverage report
mvn clean test jacoco:report

# perform mutation testing (test quality validation)
mvn org.pitest:pitest-maven:mutationCoverage

# production build (create WAR file)
mvn clean package

# run with Docker
docker build -t maps .
docker run -p 8080:8080 maps

# check application health
curl http://localhost:8080/maps/actuator/health
```

### 4.3 Monitoring & Reporting

```bash
# view test coverage
start target/site/jacoco/index.html

# view PIT report (mutation testing)
start target/pit-reports/index.html

# application metrics (Actuator)
curl http://localhost:8080/maps/actuator/metrics

# stop the application on the specific port (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

## 5. DEPLOY

### 5.1 Docker

```bash
# image build
docker build -t maps:latest .

# run with Docker Compose (includes PostgreSQL + PostGIS)
docker-compose up -d

# run only the application
docker run -d \
  --name maps \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_HOST=host.docker.internal \
  -e DB_PASSWORD=your_password \
  maps:latest
```

### 5.2. Deploy in Cloud Provider

#### __AWS ECS / Azure Container Instances__
```yaml
# docker-compose.prod.yml exemplo
version: '3.8'
services:
  maps:
    image: maps:latest
    ports:
      - "80:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prd
      - DB_HOST=${DB_HOST}
      - DB_PASSWORD=${DB_PASSWORD}
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/maps/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
```

### 5.3. Traditional Deployment (Tomcat Server)

```bash
# generate WAR file
mvn clean package -Pprd

# deploy on linux server
service tomcat stop
rm /opt/tomcat/webapps/maps*.war
rm -Rfv /opt/tomcat/webapps/maps*
cp target/maps*.war /opt/tomcat/webapps/maps.war
chown tomcat:tomcat /opt/tomcat/webapps/maps.war
chmod 755 /opt/tomcat/webapps/maps.war
service tomcat start

# check deployment
curl http://your-server:8080/maps/actuator/health
```
