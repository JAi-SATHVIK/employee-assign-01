## employee-assign-01

Small Spring Boot REST API to manage employees. It supports create, read, update and delete, plus three search approaches (JPA Specifications, HQL, Native SQL). H2 in‑memory DB is used.

### What’s inside
- `src/main/java/com/assignment/employee/EmployeeApiApplication.java` – app entrypoint
- `entity/Employee.java` – JPA entity
- `repository/EmployeeRepository.java` + `EmployeeSpecifications.java` – data access (Specifications/HQL/Native)
- `service/EmployeeService.java` – business logic
- `controller/EmployeeController.java` – REST endpoints
- `dto/*.java` – request/response models and validation
- `exception/GlobalExceptionHandler.java` – error responses
- `src/main/resources/application.properties` – H2 + JPA config
- Tests: `src/test/java/.../service`, `.../controller`, `.../integration`

### How to run
1) Java 17+ and Maven installed
2) From project root:

```bash
mvn spring-boot:run
```

App starts at `http://localhost:8080`.
H2 console: `http://localhost:8080/h2-console`
JDBC: `jdbc:h2:mem:employeedb`, user `sa`, no password.

### Quick test (cURL)
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","phone":"1234567890"}'

curl http://localhost:8080/api/employees/email/john@example.com/specifications
```

### Endpoints
- POST `/api/employees`
- GET `/api/employees/email/{email}/specifications|hql|native`
- GET `/api/employees/name/{name}/specifications|hql|native`
- PUT `/api/employees/{email}`
- PATCH `/api/employees/{email}/phone`
- DELETE `/api/employees/{email}`

### Run tests
```bash
mvn test
```


