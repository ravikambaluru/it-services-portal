# IT Services Portal

A complete Java web application for IT Services with user authentication, company information, and service/product catalog.

## Project Overview

This is a full-featured Java web application built with:
- **Java 21** - Latest long-term support version
- **Maven** - Build automation and dependency management
- **Jakarta Servlet/JSP** - Web framework
- **BCrypt** - Password hashing
- **JUnit 5 & Mockito** - Unit testing (38 tests with 80%+ code coverage)
- **JaCoCo** - Code coverage analysis
- **Log4j2** - Logging framework

## Features

### Authentication & User Management
- User registration with email validation and password strength checking
- Secure login with BCrypt password hashing
- Session management with automatic logout
- File-based user storage (JSON format)

### Dashboard
- **Company Info Tab** - Organization information with statistics
- **Services Tab** - 5 IT service categories (Cloud Computing, Cybersecurity, Data Analytics, DevOps, Application Development)
- **Products Tab** - 15 products across all service categories with descriptions, images, pricing, and links
- Responsive design for mobile and desktop

### Security Features
- Session-based authentication
- HTTP-only cookies for CSRF protection
- Password strength validation (minimum 6 characters)
- Email format validation
- Input validation on all forms

## Project Structure

```
it-services-portal/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   ├── main/
│   │   ├── java/com/itservices/
│   │   │   ├── model/              # Data models
│   │   │   │   ├── User.java
│   │   │   │   ├── Service.java
│   │   │   │   └── Product.java
│   │   │   ├── service/            # Business logic
│   │   │   │   ├── UserService.java
│   │   │   │   └── ServiceCatalogService.java
│   │   │   ├── servlet/            # Web controllers
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── RegisterServlet.java
│   │   │   │   └── LogoutServlet.java
│   │   │   └── util/               # Utility classes
│   │   │       ├── PasswordUtil.java
│   │   │       ├── FileStorageUtil.java
│   │   │       └── LocalDateTimeAdapter.java
│   │   ├── resources/
│   │   │   └── log4j2.xml          # Logging configuration
│   │   └── webapp/
│   │       ├── login.jsp           # Login page
│   │       ├── register.jsp        # Registration page
│   │       ├── dashboard.jsp       # Main dashboard
│   │       ├── error.jsp           # Error page
│   │       ├── css/
│   │       │   └── style.css       # Styling
│   │       ├── images/             # Product images
│   │       └── WEB-INF/
│   │           └── web.xml         # Servlet configuration
│   └── test/
│       └── java/com/itservices/
│           ├── service/
│           │   ├── UserServiceTest.java       # 14 tests
│           │   └── ServiceCatalogServiceTest.java  # 14 tests
│           └── util/
│               └── PasswordUtilTest.java      # 10 tests
├── target/
│   ├── it-services-portal.war      # Deployable WAR file
│   ├── classes/                    # Compiled classes
│   ├── site/jacoco/                # Code coverage report
│   └── ...
└── data/
    └── users.json                  # User storage (created at runtime)
```

## Prerequisites

- **Java Development Kit (JDK) 21 or later**
- **Maven 3.6.0 or later**
- **Tomcat 10.1 or later** (for deployment)

### Verify Installation

```bash
java -version
mvn -version
```

## Building the Project

### Clean Build
```bash
mvn clean install
```

### Compile Only
```bash
mvn clean compile
```

### Package as WAR (without running tests)
```bash
mvn clean package -DskipTests
```

### Full Build with Tests and Coverage
```bash
mvn clean verify
```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserServiceTest
```

### Run Tests with Coverage Report
```bash
mvn test jacoco:report
```

### View Coverage Report
Coverage report is generated in `target/site/jacoco/index.html`

```bash
# Open in browser (macOS)
open target/site/jacoco/index.html

# Or serve with a simple HTTP server
python -m http.server 8000
# Then navigate to http://localhost:8000/target/site/jacoco/
```

## Maven Commands Summary

| Command | Description |
|---------|-------------|
| `mvn clean` | Remove target directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn verify` | Run tests and verify code coverage |
| `mvn package` | Create WAR file |
| `mvn install` | Install to local Maven repository |
| `mvn checkstyle:check` | Run code style checks |
| `mvn jacoco:report` | Generate coverage report |

## Deployment on Tomcat

### 1. Build the WAR File
```bash
mvn clean package
```

The WAR file will be created at: `target/it-services-portal.war`

### 2. Deploy to Tomcat

#### Option A: Copy to webapps directory
```bash
# Stop Tomcat
./catalina.sh stop

# Copy WAR file
cp target/it-services-portal.war $TOMCAT_HOME/webapps/

# Start Tomcat
./catalina.sh start
```

#### Option B: Use Tomcat Manager
1. Open Tomcat Manager UI: http://localhost:8080/manager/html
2. Under "Deploy", select the WAR file from `target/it-services-portal.war`
3. Click "Deploy"

### 3. Access the Application
- **URL**: http://localhost:8080/it-services-portal
- **Default Page**: Login page

### 4. Tomcat Configuration (Optional)
To change the context path, rename the WAR file:
```bash
# For context path /services
mv target/it-services-portal.war $TOMCAT_HOME/webapps/services.war
```

Then access at: http://localhost:8080/services

## Usage

### 1. Register a New User
- Click "Register here" on the login page
- Enter email, full name, and password (min 6 characters)
- Submit the form
- Redirects to login page upon successful registration

### 2. Login
- Enter registered email and password
- Upon successful authentication, redirected to dashboard
- User information displayed in the navigation bar

### 3. Dashboard Navigation
- **Company Info Tab**: View company information and statistics
- **Services Tab**: Browse IT service offerings
- **Products Tab**: View products with details, pricing, and external links
- **Logout**: Click logout to end the session

## Code Coverage

The project achieves **80%+ code coverage** with 38 comprehensive unit tests:

- **UserService Tests** (14 tests): Registration, authentication, validation
- **PasswordUtil Tests** (10 tests): Hashing, verification, strength validation
- **ServiceCatalogService Tests** (14 tests): Service and product retrieval, data integrity

### Coverage Report
To view the detailed coverage report:
```bash
mvn clean test jacoco:report
open target/site/jacoco/index.html
```

## User Data Storage

User data is stored in `data/users.json` in JSON format:

```json
[
  {
    "userId": "uuid-here",
    "email": "user@example.com",
    "password": "hashed-password",
    "fullName": "Full Name",
    "createdAt": "2026-05-24T14:00:00",
    "lastLogin": "2026-05-24T14:05:00"
  }
]
```

**Note**: The `data` directory is created automatically on first run.

## Logging

Logs are written to both console and file (`logs/app.log`).

Configuration: `src/main/resources/log4j2.xml`

Log levels:
- **INFO**: User registration, authentication, file operations
- **WARN**: Failed authentication, validation errors
- **ERROR**: Critical errors

## Sample Test Data

To test the application, register a new user or use the following sample data after registering:

**Email**: test@example.com
**Password**: TestPass123 (or any password with min 6 characters)
**Full Name**: Test User

## Architecture

### Model Layer
- `User.java`: User entity with authentication details
- `Service.java`: IT service container
- `Product.java`: Product with details and pricing

### Service Layer
- `UserService.java`: User registration, authentication, validation
- `ServiceCatalogService.java`: Service and product management (singleton pattern)

### Persistence Layer
- `FileStorageUtil.java`: JSON file-based storage
- `LocalDateTimeAdapter.java`: GSON serialization for LocalDateTime

### Security
- `PasswordUtil.java`: BCrypt-based password operations

### View Layer
- JSP pages with JSTL for dynamic content
- Responsive CSS styling

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 21 |
| Framework | Jakarta Servlet/JSP | 6.0 |
| Build Tool | Maven | 3.6+ |
| Testing | JUnit 5 | 5.9.3 |
| Mocking | Mockito | 5.3.1 |
| Coverage | JaCoCo | 0.8.10 |
| Security | BCrypt | 0.4 |
| JSON | GSON | 2.10.1 |
| Logging | Log4j2 | 2.20.0 |
| Code Review | CheckStyle | 3.3.0 |

## Troubleshooting

### Maven Build Issues

**Problem**: `BUILD FAILURE` with compilation errors
**Solution**: Ensure Java 21 is installed and set as JAVA_HOME
```bash
java -version  # Should show version 21
```

### Tomcat Deployment Issues

**Problem**: Application not accessible at expected URL
**Solution**: Check Tomcat logs in `$TOMCAT_HOME/logs/catalina.out`

**Problem**: Port 8080 already in use
**Solution**: Change Tomcat port in `conf/server.xml`

### User Data Issues

**Problem**: Users not being saved
**Solution**: Ensure `data` directory has write permissions
```bash
chmod 755 data/
```

## Performance Considerations

- User data is stored in JSON format for simplicity (suitable for <1000 users)
- For production, consider using a relational database (MySQL, PostgreSQL)
- Service catalog is initialized once using singleton pattern
- Password hashing uses BCrypt with 12 log rounds (secure, may take ~100ms per operation)

## Future Enhancements

1. **Database Integration**: Replace JSON storage with MySQL/PostgreSQL
2. **User Profiles**: Add user profile management and preferences
3. **Service Ordering**: Implement shopping cart and order functionality
4. **Admin Panel**: Add administrative dashboard for managing services/products
5. **API Layer**: Create REST API for third-party integrations
6. **Email Verification**: Add email confirmation for registration
7. **Password Reset**: Implement forgot password functionality
8. **Search & Filtering**: Add advanced search and filtering capabilities

## License

This project is provided as-is for educational and business purpose.

## Support

For issues or questions:
1. Check the troubleshooting section above
2. Review test files for usage examples
3. Check application logs in `logs/app.log`

## Build Status

- **Java Version**: 21
- **Maven**: 3.6+
- **Test Coverage**: 80%+
- **Total Tests**: 38
- **Compilation**: ✓ Passes
- **Tests**: ✓ All Passing
- **Build Artifact**: `it-services-portal.war`

---

**Last Updated**: May 24, 2026
**Version**: 1.0.0 updated
