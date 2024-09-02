# Online Banking Platform

## Overview

The Online Banking Platform is a full-stack web application developed to provide a hands-on understanding of how a banking application works, with the main focus on account statement generation. The platform allows users to authenticate, view transactions within a specified date range, and generate account statements as PDFs, complete with digital signatures. It also includes full CRUD (Create, Read, Update, Delete) operations for managing transactions. 

**Note:** The CRUD operations on transactions are implemented solely for educational purposes to demonstrate how backend operations can be managed. These features would not typically be included in the frontend of an actual banking application due to security and operational integrity concerns.

## Tech Stack

- **Backend:**
  - **Spring Boot:** Framework for building the backend, ensuring robust data management and efficient database interactions.
  - **MySQL:** Relational database used for storing user information and transaction data.
  - **Hibernate:** ORM framework used to map Java objects to database tables.
  - **iText:** Library used for generating PDFs with digital signatures.

- **Frontend:**
  - **Angular:** Framework for building a responsive and user-friendly interface.

## Setup

### Prerequisites

- **Java 11 or higher**
- **Maven**
- **MySQL**
- **Node.js & npm**
- **Angular CLI**

### Backend Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/shreyam462/OnlineBankingPlatform.git
   cd OnlineBankingPlatform/Backend
   ```

2. **Configure MySQL:**

   - Create a database named `banking_app`.
   - Update the database connection properties in `application.properties` located in `src/main/resources/`.

3. **Set up the database schema:**

   Execute the `db.sql` file located in the root directory to set up the necessary database schema:

   ```bash
   mysql -u <username> -p<password> 
   banking_app < ../db.sql
   ```

4. **Build and run the backend:**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Navigate to the frontend directory:**

   ```bash
   cd ../Frontend
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Run the Angular development server:**

   ```bash
   ng serve
   ```

   The application will be available at `http://localhost:4200`.

## media\screenshots

### Landing Page

![Landing Page](media\screenshots\landing_page.png)

### Login Page

![Login Page](media\screenshots\login_page.png)

### Home Page

![Home Page](media\screenshots\home_page.png)
![Home Page](media\screenshots\home_date.png)
![Home Page](media\screenshots\home_view_transactions.png)

### Generated PDF

![Generated PDF](media\screenshots\pdf_unsigned.png)
![Generated PDF](media\screenshots\pdf_signed.png)
![Generated PDF](media\screenshots\pdf_signed_zoomed.png)

### Transactions CRUD

![Transactions CRUD](media\screenshots\transactionCRUD_get.png)
![Transactions CRUD](media\screenshots\transactionCRUD_edit.png)
![Transactions CRUD](media\screenshots\transactionCRUD_add.png)

## Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss any changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For any questions or inquiries, please contact [shreya.m462@gmail.com](mailto:shreya.m462@gmail.com).