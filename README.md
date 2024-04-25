# Application Requirements

## Application User

### 1. User new account (unique email address)

- Account Verification (verify email address)
- User profile image
- User details (name, email, position, bio, phone, address, etc.)
- Being unable to update user detail information

### 2. User reset password (without being logged in)

- Password reset link should expire within 24 hours

### 3. User login (using email and password)

- Token based authentication (JWT Token)
- Refresh token seamlessly

### 4. Brute force attack mitigation (account lock mechanism)

- Lock account on 6 failed login attempts

### 5. Role and Permission based application access (ACL)

- Protect application resources using roles and permissions

### 6. Tow-factor Authentication (using user phone number)

- Send verification code to user's phone

### 7. Keep track of user activities (login, account change, etc.)

- Ability to report suspicious activities
- Tracking information:
    -- IP Address
    -- Device
    -- Browser
    -- Date
    -- Type

## Customers

### 1. Customer information

- Manage customer information (name, address, etc.)
- Customer can be a person or an organisation
- Customer should have a status
- Customer will have invoices
- Print customers in spreadsheets

### 2. Search Customers

- Be able to search customers by name
- Pagination

## Invoices
