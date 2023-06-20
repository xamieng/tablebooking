# Online Table Booking API

## SET UP THE API

### 1. PREREQUISITE
- docker

### 2. BUILD JAR file
```
./gradlew build
```

### 3. BUILD Docker image
```
docker-compose build
```

### 4. START API
```
docker-compose -f docker-compose.yml up -d
```

### 5. STOP API
```
docker-compose -f docker-compose.yml down
```

### 6. API Specification
```
http://localhost:8080/swagger-ui/index.html#/
```
