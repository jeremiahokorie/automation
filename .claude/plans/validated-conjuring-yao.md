# Plan: Authenticate User using ABSIN

## Context
The user wants to implement an additional means of authentication for users. Currently, the system only supports email/password login. The goal is to allow users to authenticate using their Abia State Identification Number (ABSIN), provided it is valid and approved.

## Proposed Implementation

### 1. Database & Model Update
- **User Model**: The `User` entity currently contains `nin` but does not have a field for `absin`.
- **Action**: Add a `private String absin` field to `com.automation.core.global.model.User`. This ensures that once a user is linked to an ABSIN, it is stored in their profile for quick lookup during authentication.

### 2. Repository Update
- **UserRepository**: Add a method to find a user by their ABSIN.
- **Action**: Add `Optional<User> findByAbsin(String absin);` to `com.automation.core.global.repository.UserRepository`.

### 3. Service Layer Implementation
- **Authentication Logic**: 
    - We need a new method in the authentication service (or `UserService`) to handle ABSIN-based authentication.
    - The flow should be:
        1. Receive `absin` from the request.
        2. Check if the `absin` is valid and approved using `AbiaStateIdentificationService.verifyByAbiaIdNumber(absin)`.
        3. Once verified, find the corresponding `User` in the system via `UserRepository.findByAbsin(absin)` or by matching the email from the `AbiaStateIdentification` record.
        4. If the user exists and is linked, generate a JWT token using `JwtUtil`.
- **Error Handling**: 
    - If `AbiaStateIdentificationService` throws a `ResourceNotFoundException` (invalid or not approved), we should map this to a clear authentication error (e.g., "Invalid or unapproved ABSIN number").
    - If no user is linked to that ABSIN in the `user` table, throw a "User profile not found" error.

### 4. API Endpoint
- **AuthController**: Add a new endpoint for ABSIN authentication.
- **Action**: Create `@PostMapping("/login-absin")` which accepts a request containing the `absin`.
- **Response**: Return a standard `AuthResponse` containing the JWT token upon successful authentication.

### Critical Files to Modify:
- `src/main/java/com/automation/core/global/model/User.java`: Add `absin` field.
- `src/main/java/com/automation/core/global/repository/UserRepository.java`: Add `findByAbsin`.
- `src/main/java/com/automation/core/global/controller/AuthController.java`: Add `/login-absin` endpoint.
- `src/main/java/com/automation/core/global/service/ServiceImpl/AuthenticationService.java` (or equivalent): Implement the ABSIN authentication logic.

## Verification Plan
1. **Database Migration**: Verify that the `user` table has the `absin` column.
2. **Positive Test**:
    - Create an `AbiaStateIdentification` record and set its status to `APPROVED`.
    - Link this ABSIN to a `User` profile.
    - Call `/auth/login-absin` with the ABSIN and verify a JWT is returned.
3. **Negative Tests**:
    - Use an ABSIN that does not exist $\rightarrow$ Expect "Invalid or unapproved ABSIN number".
    - Use an ABSIN that exists but is `PENDING` $\rightarrow$ Expect "Invalid or unapproved ABSIN number".
    - Use an approved ABSIN that is not linked to any `User` profile $\rightarrow$ Expect "User profile not found".
