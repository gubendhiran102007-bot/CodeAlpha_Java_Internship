# Hotel Reservation System

Java 17 + Spring Boot + MySQL starter project.

## 1. Requirements
- JDK 17 or newer
- IntelliJ IDEA Community Edition
- MySQL Server
- Internet access for Maven dependency downloads

## 2. Configure MySQL
Create the database in MySQL Workbench or the MySQL command line:

```sql
CREATE DATABASE hotel_db;
```

Open `src/main/resources/application.properties` and replace `YOUR_MYSQL_PASSWORD` with your local MySQL password. If your MySQL username is not `root`, change that too.

## 3. Run in IntelliJ
1. Open the extracted project folder.
2. Allow IntelliJ to import the Maven project and download dependencies.
3. Ensure Project SDK is JDK 17.
4. Open `HotelreservationApplication.java`.
5. Click the green Run icon next to `main`.

The application runs at `http://localhost:8080`. On first start, three sample rooms are inserted automatically.

## 4. Test with Postman

### List rooms
`GET http://localhost:8080/api/rooms`

### Create booking
`POST http://localhost:8080/api/bookings`
Header: `Content-Type: application/json`

```json
{
  "customerName": "Arun Kumar",
  "customerEmail": "arun@example.com",
  "roomId": 1,
  "checkIn": "2026-11-10",
  "checkOut": "2026-11-13"
}
```

Use future dates for your actual test.

### List bookings
`GET http://localhost:8080/api/bookings`

### Get one booking
`GET http://localhost:8080/api/bookings/1`

### Cancel booking
`PUT http://localhost:8080/api/bookings/1/cancel`

### Simulate payment
`PUT http://localhost:8080/api/bookings/1/pay-demo`

**Important:** `pay-demo` is only a learning simulation. It does not collect real money. For real payments, integrate a provider such as Razorpay with server-side order creation, signature verification, and webhook handling. Before production, add authentication/authorization, restrict CORS, use decimal money types, and protect booking creation against concurrent double-booking.
