project participants: samantha murillo carmona: 221622816, Ephratah: ,Jonathan: , Parham:


```text
src/main/java/com/example/springboot/
├── model/
│   └── PaymentRequest.java        <-- [UC6] Secure Data Storage / DTOs
├── strategy/
│   ├── PaymentStrategy.java       <-- [Pattern] GoF Strategy Interface
│   ├── CreditCardStrategy.java    <-- [UC5] Card Validation Logic
│   ├── PayPalStrategy.java        <-- [UC5] PayPal Integration Logic
│   └── BankTransferStrategy.java  <-- [UC5] Bank Transfer Logic
├── service/
│   ├── PaymentService.java        <-- [UC5] Payment Workflow Orchestration
│   ├── PaymentMethodService.java  <-- [UC6] Payment Method CRUD Management
│   └── BookingHistoryService.java <-- [UC4] Transaction History & Audit Logic
└── repository/
    └── BookingRepository.java     <-- Data Access Layer (JPA/Hibernate)
```

    ### 🛡️ Payment Subsystem Integration Status (UC4-UC7)
I have completed the backend logic for the following:
* **UC4 (Booking History):** Service ready to fetch from `BookingRepository`.
* **UC5 (Payment Processing):** **Strategy Pattern** implemented for Credit/Debit, PayPal, and Bank Transfer. Includes 2-3s simulation delay.
* **UC6 (Manage Methods):** CRUD operations for saved payment profiles.
* **UC7 (Payment History):** Tracking for successful/pending payments and refunds.

**Note to UC1-UC3 devs:** My `PaymentService` is ready to receive your `Booking` objects. Just ensure your `Booking` entity has a `setStatus()` method so I can transition it to `PAID` after the simulation.

### Applied Design Patterns (GoF)

1. **Strategy Pattern**: Used to encapsulate different validation and processing algorithms for Credit Card, PayPal, and Bank Transfers. This allows for easy extension of new payment methods without modifying the core service.
2. **Factory Pattern**: Implemented `PaymentStrategyFactory` to decouple the creation and selection of payment strategies from the `PaymentService`. This centralizes the strategy lookup logic.
3. 1. State Pattern
Where: BookingState.java + BookingLifecycleService.java + Booking.java

The State Pattern is used to model the booking lifecycle from Section 5. The BookingState enum defines all seven possible states a booking can be in (REQUESTED, CONFIRMED, PENDING_PAYMENT, PAID, REJECTED, CANCELLED, COMPLETED). The BookingLifecycleService acts as the state manager — it holds a static EnumMap<BookingState, Set<BookingState>> called TRANSITIONS that maps every state to the exact set of states it is allowed to move to. When any part of the system needs to change a booking's state, it must call BookingLifecycleService.transition(), which resolves the current state, checks the transition map, and either returns the new state or throws an IllegalStateException if the transition is illegal. The Booking model itself stores the current state as a String and exposes getState() and setState(), but all transition logic is externalized into BookingLifecycleService so that no invalid state change can bypass the rules. Terminal states (Rejected, Cancelled, Completed) map to empty sets, meaning once a booking reaches one of those states, it cannot transition any further. This pattern was chosen because the booking has clearly defined states with strict rules governing how it moves between them, which is exactly the problem the State Pattern is designed to solve.

Consultant Subsystem (UC8 - UC10)
Consultant subsytem backend logic is complete for the following:
**UC8 (Manage Availability):** Consultants have a list of avalabilities and they are able to add and remove availbilties. Availabilty Factory is used to generate availability objects ensuring all availability instances are created in a consistent way. 
**UC9 (Accept or Reject Booking Request):** When a Client requests a booking session, a booking request is made and sent to the consultant. The consultant is able to accept or reject the booking and the system uses an Observer pattern to notify clients of the booking state after. 
**UC10 (Complete a Booking):** After a consulting session occurs, the consultant marks the booking as completed.

src/main/java/com/example/springboot/
├── model/
│   ├── Consultant.java               <-- [UC8] Consultant Entity & Availability List
│   ├── AvailabilityStatus.java       <-- [UC8] Enum: AVAILABLE, BOOKED, CANCELLED
│   └── AvailabilityFactory.java             <-- [UC8] GoF Factory Pattern 
├── observer/
│   ├── BookingObserver.java             <-- [UC9 & UC10] Booking Logic Layer
│   └── ClientNotificationObserver.java   <-- [UC9] Notifies Client of booking status
├── service/
│   ├── impl/
│   ├── AvailabilityServiceImpl.java             <-- [UC8] Availability Logic Layer
│   └── AvalabilityService.java           

    
Admin Subsystem Integration Status (UC11-UC12)

Admin subsystem backend logic is complete for the following:

* **UC11 (Approve Consultant Registration):** Service ready to approve or reject consultants. Consultant status transitions: `PENDING` → `APPROVED` or `REJECTED`.
* **UC12 (Define System Policies):** Full policy management implemented via `PolicyManager` singleton. Supports adding, updating, viewing, and removing system-wide policies (cancellation rules, refund rates, pricing strategy, notification settings).

Admin Subsystem File Structure

```text
src/main/java/com/example/springboot/
├── model/
│   ├── Consultant.java               <-- [UC11] Consultant Entity & Registration Status
│   ├── RegistrationStatus.java       <-- [UC11] Enum: PENDING, APPROVED, REJECTED
│   └── SystemPolicy.java             <-- [UC12] Policy Entity (name, value, description)
├── service/
│   ├── AdminService.java             <-- [UC11 & UC12] Business Logic Layer
│   └── PolicyManager.java            <-- [Pattern] GoF Singleton - System Policy Store
└── controller/
    └── AdminController.java          <-- [UC11 & UC12] REST API Endpoints
```
Applied Design Patterns (GoF) — Admin Subsystem

3. **Singleton Pattern**: `PolicyManager` ensures only one instance of the policy store exists across the entire application. All services read from the same policy state, preventing inconsistencies in cancellation rules, refund rates, and platform fees.


To run the backend, navigate to the /backend directory and execute ./mvnw spring-boot:run.

## Client Booking Subsystem – File Structure (UC1, UC2, UC3)

The files below implement the client-facing booking flow: browsing services, requesting a booking, and cancelling a booking, along with the full booking lifecycle defined in Section 5.

```text
src/main/java/com/example/springboot/
├── model/
│   ├── BookingState.java                    <-- [Section 5] Enum defining all 7 booking lifecycle states
│   ├── Booking.java                         <-- [Core] The central booking entity linking client, consultant, and state
│   ├── ConsultingServiceInfo.java           <-- [UC1] Model representing a browsable consulting service
│   ├── BookingRequestDTO.java               <-- [UC2] DTO carrying the client's booking request data
│   └── CancellationResult.java              <-- [UC3] DTO returned after a cancellation attempt
├── service/
│   ├── ConsultingServiceCatalogService.java <-- [UC1] Manages the service catalogue and browse/filter logic
│   ├── ClientBookingService.java            <-- [UC2/UC3] Core service for creating and cancelling bookings
│   └── BookingLifecycleService.java         <-- [Section 5] Enforces legal state transitions for all bookings
└── controller/
    └── ClientController.java                <-- [UC1/UC2/UC3] REST API endpoints under /api/client
```



